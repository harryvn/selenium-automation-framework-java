#!/bin/bash

# Display introductory information
echo "Welcome to the Automated Selenium Test Runner!"
echo "This script facilitates the configuration and execution of Selenium tests with Maven."
echo "You will be prompted to select various options such as browser choice, local or remote execution, Selenium Grid configuration, video recording, parallel execution, and headless mode."
echo "Once all configurations are set, the script will construct the appropriate Maven command and execute it to run the Selenium tests."
echo ""

# Prompt user to continue or quit
read -rsp $'Press Enter to continue or Ctrl + C to quit...\n'

# Function definitions and main script follow...

################################################################################

# Function to prompt the user to select an option from a list
# Parameters:
#   $1: The question to prompt the user with
#   $@: The list of options to choose from
prompt_select() {
    # Set the question variable to the first parameter
    local question=$1
    shift
    # Set the options array to the remaining parameters
    local options=("$@")
    # Set the prompt message
    local PS3="$question "
    # Prompt the user to select an option from the list
    select opt in "${options[@]}"; do
        # Check if the user selected a valid option
        if [[ -n $opt ]]; then
            # Print the selected option and exit the loop
            echo "$opt"
            break
        else
            # Print an error message and prompt the user again
            echo "Invalid option. Please select a number."
        fi
    done
}

# Function to prompt user for a boolean choice
# Parameters:
#   $1: The question to ask the user
prompt_boolean() {
    local question=$1
    local result
    # Set the prompt message
    PS3="$question "
    # Display options and read user choice
    select opt in "true" "false"; do
        case $opt in
            "true" ) result="true"; break;;
            "false" ) result="false"; break;;
            * ) echo "Invalid option. Please select true or false.";;
        esac
    done
    # Output the selected result
    echo "$result"
}

# Prompt for browser
echo "Select preferred browser:"
browsers=("CHROME" "FIREFOX" "EDGE")
browser=$(prompt_select "Enter the number corresponding to your choice:" "${browsers[@]}")

# Ask if script will be run locally or remote
echo "Will the script be run locally or remotely?"
run_types=("LOCAL" "REMOTE")
run_type=$(prompt_select "Enter the number corresponding to your choice:" "${run_types[@]}")

# If run remotely, ask for remote grid IP or hostname
if [ "$run_type" == "REMOTE" ]; then
    while true; do
        read -rp "Enter remote Selenium Grid IP or hostname: " remote_grid_ip
        # Check if input is numeric
        if ! [[ $remote_grid_ip =~ ^[0-9.a-zA-Z]+$ ]]; then
            echo "Error: Please enter a valid IP or hostname."
            continue
        fi
        read -rp "Enter remote Selenium Grid port: " remote_grid_port
        # Check if input is numeric
        if ! [[ $remote_grid_port =~ ^[0-9]+$ ]]; then
            echo "Error: Please enter a valid numeric port."
            continue
        fi

        # Construct the remote grid URL
        remote_grid_url="http://$remote_grid_ip:$remote_grid_port"

        # Break the loop if validation passes
        break
    done

    # Ask if the user wants to record video
    echo "Do you want to record video?"
    record_video=$(prompt_select "Enter the number corresponding to your choice:" "Yes" "No")

    case $record_video in
        "Yes" ) record_video="yes";;
        "No" ) record_video="no";;
        * ) echo "Invalid option. Defaulting to 'no'."; record_video="no";;
    esac
else
    remote_grid_url=""
    record_video="no"
fi

# Ask if scripts will be executed in parallel
echo "Do you want to execute scripts in parallel?"
parallel_options=("None" "Tests")
parallel=$(prompt_select "Enter the number corresponding to your choice:" "${parallel_options[@]}")

case $parallel in
    "None" ) parallel="none";;
    "Tests" ) parallel="tests";;
    * ) echo "Invalid option. Defaulting to 'none'."; parallel="none";;
esac

# Ask if scripts will be executed in headless mode
echo "Do you want to execute scripts in headless mode?"
headless=$(prompt_boolean "Enter the number corresponding to your choice:" "1. true" "2. false")

# Check if headless mode is enabled and video recording is requested
if [ "$headless" == "true" ] && [ "$record_video" == "yes" ]; then
    echo "Note: Video recording is not supported in headless mode. Disabling video recording."
    record_video="no"
fi

# Formulate the command
command="mvn clean test -Denv=$run_type -Dbrowser=$browser"

if [ "$run_type" == "REMOTE" ]; then
    command+=" -DremoteSeleniumGridUrl=$remote_grid_url"
    if [ "$record_video" == "yes" ]; then
        command+=" -DrecordVideo=true"
    else
        command+=" -DrecordVideo=false"
    fi
fi

command+=" -Dparallel=$parallel -Dheadless=$headless"

# Execute the command
echo "Executing command: $command"
# Uncomment the line below to execute the command
$command
