# TomNJerry
AI Tom chasing greedy Jerry
AI Tom use: DFS BFS A* search to chase Jerry

About this game:

![](/image/Game%20Description.png)
As game start, Tom (cat), Jerry(mouse) and 3 cheese will be place in random location on the board. 
Greedy Jerry always found the nearest cheese to eat.
If Jerry eats all cheese on board, Jerry won.
If Tom catch Jerry before Jerry eat all cheese, Tom Win.
Use console to see the graphic repersentation. 
this is a java version of searching. 
GUI is still developing in JS & React. 

How to run:
complie each Java file and run Board.main()

The console will print out the shorest path to chase Jerry solved by BFS and A*, as well as the cost of each algorithm.
There are two heuristic functions used in game.
