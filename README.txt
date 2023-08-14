Image Processing Program

Interfaces:
ImageProcessController - this is the controller interface
		       - it reads a ImageProcessModel to run the program
ImageProcessModel - this is the model interface
		  - this will outline methods to store images with its names
ImageProcessView - this is the view interface
		 - this will output to the controller
ImageCommand - this is the command interface
	     - will run this command on the given model
ImageFrame - interface that has the method to add a BufferedImage
ImageProcessGUIView - interface that supports methods for GUI interface support
Features - interface which represents the interface for GUIController to complete commands

Classes:
ImageController - this is the implementation of the ImageProcessController
		- stores model, view, readable, and a map of name to function
		- runs the program
GUIController - implementation of Features which allows it to complete commands by executing the
	        ImageCommand passed as a parameter
	      - contains a model and view to appropriately communicate betwen the two

ImageModel - this will store a name with its corresponding image defined by a 2d array in a map
	   - this also stores methods to modify the map and apply a command to an image

Brighten - this is a command to brighten or darken an image
Component - this is a command to do one of many component commands which will take the
	    red, green, blue, value, intensity, luma component of an image
Grayscale - command that supports showing the sepia, blurred, and sharpened version of an iamge
HorizontalFlip - this will horizontally flip an image
VerticalFlip - this will vertically flip an image
Load - this will load a iamge with the given path into the model
Save - this will save a given image into a path

ImageView - this will output a string to the appendable

ImageProcess - this holds the main method to run the program

ImageUtil - has the functions to convert a given file and store its contents in a 2d array of Colors
	  - can read JPEG, PNG, BMP, and PPM
	  - also retrieves the extension of a file

note: 
- words inside two dashes(-word-) represents a parameter for
  that specific command
- words after hashtag in given line are comments

Commands inside the program along with its parameters:

load -reference name- —path of file-
save -reference name- -path to save-
horizontal-flip —file to flip-
verticla-flip —file to flip-
brighten —file to change- —brighten value-	# positive value to brighen / negative to darken   
component —reference file- -modified file- -a component type-

a component is one of the following strings:
- red
- green
- blue
- value
- intensity
- luma
- blur
- sharpen
- sepia

Examples of some of the commands:
notes: 
- make sure if using relative path to be in that directory or simply provide the full path
- the program supports 3 different ways to run(any after the “*” shows the command for that feature):
	- command-line program (where commands are manually typed)
		* java -jar hw6.jar -txt
	- through script file (when the program runs a scripfile with commands)
		* java -jar hw6.jar -file -path of script—
	- through graphical interface
		* java -jar hw6.jar

load image1 res/flower.ppm
brighten image1 30
brighten image1 -40
horizontal-flip image1
component image1 image2 luma
save image2 res/four-operationed-flower.ppm

# this series of commands will brighten iamge1 by 30, then by -40, followed by flipping it
# horizontally, and then getting the luma component of image1 and making image2 be that 
# luma component, and finally saving the image2 in the relative path res/four-operationed-flower.ppm

source of flower.ppm:
Zhang Tingrui (owner and contributer to this project)

source of screenshotofhw.png:
Josan Limbu: screenshot of his screen, with a loaded png picture from Riot Games

HW5 Update:
- moved the ImageUtil class, ImageCommand interface and its command classes to the “commands” package
- added the blur, sharpen, sepia component to the Component class command
- added method in the ImageUtil class to read the extension of provided file
- ImageUtil now has methods to convert JPEG, PNG, BMP, and PPM into a 2d array of Color for use
- the Save command now can save a file as any of the following extensions: JPEG, PNG, PPM, & BMP
- main method now reads the file given to it and execute commands

HW6 Update:
- moved sepia, blur, sharpen into a new class Grayscale.java due to their similarity
- added another controller (asynchronous) to handle GUI inputs from the user
- added classes and interfaces(see above) to enable GUI program for the user