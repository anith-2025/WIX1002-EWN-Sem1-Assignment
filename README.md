# WIX1002-EWN-Sem1-Assignment
Java implementation of *EinStein würfelt nicht!* puzzle solver for WIX1002 Semester 1 assignment.

## 📂 Project Overview
This project simulates the board game puzzle with strict move limits and specific rule variants:
- **Victory Condition**: Move the target piece to position `0`.
- **Game Rules**:
  - Pieces move according to a fixed dice sequence.
  - If the dice matches a piece, it must move.
  - If the dice piece is missing, the player chooses the **Next Higher** or **Next Lower** piece.
  - **Invalid Square**: Position `22` is restricted; no piece can land there.
- **AI Solver**: Uses A* Search to find the shortest path within the move limit.

## 🎯 Level Constraints
The AI is optimized to solve each level within specific limits:
- **Level 1**: Max 6 Moves
- **Level 2**: Max 10 Moves
- **Level 3**: Max 10 Moves
- **Level 4**: Max 15 Moves

## 🚀 How to Run
### Option 1: One-Click Script (Recommended)
Double-click **`RunGame.bat`** in the project folder. This will automatically compile the code and start the game.

### Option 2: Manual Compilation
Open a terminal in the project directory and run:
```bash
javac *.java
java GameMain
```

## 🎮 Usage
1. Run the game.
2. Select **3** for **AI Player**.
3. Enter the Level number (1-4).
4. The game will find the optimal solution and generate **`moves.txt`**.
5. Load `moves.txt` into the **EWN_GUI Viewer** to watch the solution.

## 👥 Team Members
- Member 1 – GameLoader (File Input & Coordinate Parsing)
- Member 2 – GameState (Move Logic, Rules, & Constraints)
- Member 3 – HumanPlayer (User Interaction)
- Member 4 – RandomPlayer & AIPlayer (A* Search Strategy)
- Member 5 – GameMain & Move (Game Loop & Limit Enforcement)
