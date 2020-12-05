# Maze Ball
~~Shitty~~ Amazing android game for school project 
## List of features:
* Sensors - Using accelerometer for player movement
* Multimedia - Game sprites, sounds
* 2D game canvass
* Persistent storage - Shared preferences used to save user settings (chosen avatar, muted sound) and reached level
* Text file data
* Advanced GUI  
  
User has the ability to change his player avatar from the main menu. This option is saved in the persistent storage. User can also mute the game sounds either from main menu or pause game menu during playing the game. The game contains 8 levels which are saved in [levels.txt](https://github.com/ImTheBadWolf/MazeRunner/blob/player_camera/app/src/main/assets/levels.txt) file in a matrix format. When users starts the game for the first time he has only one level unlocked. Levels are progressively unlocked as he plays the game, levels get more difficult. User has the option to pause the game during playing. From the pause menu he can recalibrate the accelerometer or mute the sounds.
      

![game screen1](https://github.com/ImTheBadWolf/MazeRunner/blob/master/game%20play/sc1.jpg)
![game screen2](https://github.com/ImTheBadWolf/MazeRunner/blob/master/game%20play/sc2.jpg)
![game screen3](https://github.com/ImTheBadWolf/MazeRunner/blob/master/game%20play/sc3.jpg)

## Improvement suggestions
* Use linear interpolation for player movement  
Player movement looks "jumpy" right now as he moves from tile to tile. This could be fixed by using linear interpolation, this feature is partly implemented in the [smoother movement](https://github.com/ImTheBadWolf/MazeRunner/tree/smoother_movement) branch.  
* Camera that follows player  
Whole level is visible from the top which makes the game easier. Insted of this it would be better to use camera that follows the player and then you will see only part of the maze around the player. This feature is implemented in the [player_camera](https://github.com/ImTheBadWolf/MazeRunner/tree/player_camera) branch but is waiting for the smoother movement to be finished. 