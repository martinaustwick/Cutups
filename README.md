# Cutups
Sample n-second segments of a random .mp3 file from a specified folder. Built in Processing with the Minim library.

You may need to install Java 8 on your computer to run this; then download the folder specific to your operating system (e.g. "win64"). You will need to open preferences.json in a text editor like notepad or textedit (don't change the name) and specify the folder with your mp3 files in, and the length of the sample you want to pick in seconds.

Once you have the code running, you can press the following buttons:

'r' to choose a new random bit of the current file
'f' to choose a new random bit of a random file
't' to add a line to "tracking.csv" with the filename and timestamp of what you're listening to. Then open "tracking.csv" in excel, openoffice, or a text editor to add your own comments
