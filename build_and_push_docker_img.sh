#!/bin/bash

run_command_with_log() {
    local start_time=$(date +%s)

    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "▶️  Starting to execute command: '$1'"

    eval "$1"
    local exit_code=$?

    local end_time=$(date +%s)
    local duration=$((end_time - start_time))
    if [ $exit_code -ne 0 ]; then
        echo "❌ Error executing command: '$1'"
        echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        exit $exit_code
    fi

    echo "✅ Command successfully completed: '$1'"
    echo "⏱️ Execution took: ${duration} seconds"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
}

total_start=$(date +%s)

echo "🚀 Build and Push Docker Image script started..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""

# Build Docker Image
run_command_with_log "docker build -t us-central1-docker.pkg.dev/currency-mate-project/currency-mate-docker-images/currency-mate-srv:latest ."
echo ""

# Push Docker Image
run_command_with_log "docker push us-central1-docker.pkg.dev/currency-mate-project/currency-mate-docker-images/currency-mate-srv:latest"
echo ""

# Print overall script duration
total_end=$(date +%s)
total_duration=$((total_end - total_start))
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "🎉 Script completed successfully!"
echo "⏱️ Script overall execute took: ${total_duration} seconds"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"