package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons;
    private boolean player1Turn = true; // Player 1 is 'X', Player 2 is 'O'
    private int moveCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new Button[9];
        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "button" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button clickedButton = (Button) view;
                    if (clickedButton.getText().toString().isEmpty()) {
                        clickedButton.setText(player1Turn ? "X" : "O");
                        moveCount++;
                        if (checkForWin()) {
                            Toast.makeText(MainActivity.this, "Player " + (player1Turn ? "1" : "2") + " wins!", Toast.LENGTH_SHORT).show();
                            resetGame();
                        } else if (moveCount == 9) {
                            // It's a draw
                            Toast.makeText(MainActivity.this, "It's a draw!", Toast.LENGTH_SHORT).show();
                            resetGame();
                        } else {
                            // Switch the turn
                            player1Turn = !player1Turn;
                        }
                    }
                }
            });
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        // Populate the 3x3 matrix with the button texts
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i * 3 + j].getText().toString();
            }
        }

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].isEmpty()) {
                return true;
            }
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].isEmpty()) {
                return true;
            }
        }

        // Check diagonals
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].isEmpty()) {
            return true;
        }
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].isEmpty()) {
            return true;
        }

        return false;
    }

    private void resetGame() {
        for (Button button : buttons) {
            button.setText("");
        }
        player1Turn = true;
        moveCount = 0;
    }
}
