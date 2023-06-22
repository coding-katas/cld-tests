#!/bin/bash

style_options="
--min-conditional-indent=0
--max-instatement-indent=120
--pad-oper
--pad-header
--unpad-paren
--add-brackets
--break-after-logical
--lineend=linux
--preserve-date
--style=java
--indent=spaces=4
--convert-tabs
--max-code-length=200
"
echo "######################## Start artistic style ##############################################"

find . -name "*.java" -type f -print0 | while IFS= read -r -d '' file; do
  echo "Formatting file: $file"
  astyle -vnQ $style_options "$file"
done

