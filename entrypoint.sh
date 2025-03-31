#!/bin/sh

EXT_ALPHAVANTAGE_API_KEY=$(cat /mnt/secrets/ext-alphavantage-api-key)
EXT_EXCHANGERATE_API_KEY=$(cat /mnt/secrets/ext-exchangerate-api-key)

export EXT_ALPHAVANTAGE_API_KEY
export EXT_EXCHANGERATE_API_KEY

# Running the App
exec java $JAVA_TOOL_OPTIONS -jar /app/service.jar