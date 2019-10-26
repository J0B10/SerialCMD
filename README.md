# SerialCMD

### About
I was looking for a simple tool that allows me to communcate with my Arduino that is connected to the pc through a serial port.
As found nothing that realy fit my needs I created this simple command line tool. 

### Installation
Clone the repo with git, and use maven to create a jar. Then I suggest creating a simple bash or shell file to run the application (example [bin/serial.bat](/bin/serial.bat)) and add that file to your [PATH](https://gist.github.com/nex3/c395b2f8fd4b02068be37c961301caa7), so you can run it by typing `serial`.

Here are some commands that might be usefull:
```shell
# Clone the repo
git clone https://github.com/joblo2213/SerialCMD.git

cd SerialCMD

# Create the jar
maven package

# Run the tool
java -jar target/SerialCMD-1.0-SNAPSHOT.jar
```

## Usage
```
SerialCMD 1.0
Usage: serial [options]
  -h, --help
                show this help message
  -l, --list
                list connected serial devices
  -p, --port <value>
                open monitor for a specific port
  -b, --baud
                set the baud rate for the connection (default 9600)
```
