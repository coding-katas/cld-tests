import json
import logging
from kafka import KafkaConsumer
import ssl
import requests
import threading
import time
import sys

logging.basicConfig(level=logging.INFO, filename='logfile.log')

start_thread_request = True

CLIPER_TOPIC_EVENT_NAME = "CLIPER_TOPIC_EVENT"

if len(sys.argv) > 1:
    KAFKA_CLUSTER_INSTANCES = sys.argv[1]
else:
    KAFKA_CLUSTER_INSTANCES = 'instance-1:9093,instance-2:9093'

consumer = KafkaConsumer(bootstrap_servers=KAFKA_CLUSTER_INSTANCES,
                          security_protocol='SSL',
                          ssl_password='password',
                          ssl_check_hostname=True,
                          value_deserializer=lambda x: json.loads(x.decode("utf-8")),
                          ssl_cafile='/data/certs/truststore.pem',
                          ssl_certfile='/data/certs/certificate.cer',
                          ssl_keyfile='/data/certs/privatekey.pem')
consumer.subscribe([CLIPER_TOPIC_EVENT_NAME])

# Dictionary to store message counts by identifier
message_counts = {}
received_messages = 0
dedup_count = 5
nb_messages = 6
request_count = dedup_count * nb_messages

def post_cliper_request(entity_id, key_,  value):
    url = 'http://localhost:8080/cliper'
    headers = {'Content-Type': 'application/json'}
    data = {
        'entityId': entity_id,
        'params': [
            {
                'key': key_,
                'value': value
            }
        ]
    }
    response = requests.post(url, headers=headers, data=json.dumps(data))
    return response.json()

def send_requests():
    global start_thread_request
    key = "application_name"
    value = "tcrm"
    new_id = 0
    if start_thread_request == True:
        print("waiting...")
        time.sleep(2)
        start_thread_request = False
    print("starting requests")
    for i in range(request_count):
        identifier = "ID-" + str(new_id)
        response = post_cliper_request(identifier, key, value)
        #print(response)
        if (i%dedup_count==0):
           print("sended",i,"messages", "last one:", response)
           new_id += 1

def process_cliper_event(cliper_event):
    global received_messages
    global message_counts
    try:
        identifier = cliper_event['entityId']
        if identifier not in message_counts:
            message_counts[identifier] = 0
        message_counts[identifier] += 1
        received_messages += 1

        logging.info("Message count for identifier %s: %d", identifier, message_counts[identifier])
        current_time = time.time()
        event_timestamp = cliper_event["messageCreationTime"]
        time_diff = (current_time * 1000) - event_timestamp
#        print("event ID",cliper_event["entityId"] ,"after :",time_diff);

        logging.info("Event ID %s arrive after: %d", identifier, time_diff)

    except Exception as e:
        print("Error processing message:", identifier,e)
        logging.exception(e)

# Create a separate thread to send requests
request_thread = threading.Thread(target=send_requests)
request_thread.start()

# Start consuming messages from the Kafka stream
for message in consumer:
    cliper_event = message.value
    process_cliper_event(cliper_event)
#    print("Received messages:",received_messages, len(message_counts))
    if len(message_counts) == nb_messages:
        print(message_counts)
        # Check if all values in message_counts are equal to 1
        if all(value == 1 for value in message_counts.values()):
            print("Test OK")
        else:
            print("Test Fail")
        break

request_thread.join()
