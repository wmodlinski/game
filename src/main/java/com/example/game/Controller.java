package com.example.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Text tittleText;

    private int playerTurn = 0;

    ArrayList<Button> buttons;
    List<Button> removed = new LinkedList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9));

        buttons.forEach(button -> {
            setupButton(button);
            button.setFocusTraversable(false);
        });
    }

    @FXML
    void restartGame(ActionEvent event) {
        buttons.forEach(this::resetButton);
        tittleText.setText("Kółko i krzyżyk");
    }

    public void resetButton(Button button) {
        button.setDisable(false);
        button.setText("");
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            setPlayerSymbol(button);
            button.setDisable(true);
            boolean isGameOver = checkIfGameIsOver();

            //odpal kod dla komputera
            if (!isGameOver) {
               cpuMove();
            }
        });
    }

    public void cpuMove() {
        List<Button> allButtons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        List<Button> availableButtons = allButtons.stream()
                .filter(b -> !b.isDisabled())
                .collect(Collectors.toList());

        Random random = new Random();

        int buttonIndex = random.nextInt(availableButtons.size());

        Button cpuButton = availableButtons.get(buttonIndex);
        setPlayerSymbol(cpuButton);
        cpuButton.setDisable(true);
        checkIfGameIsOver();
    }

    public void setPlayerSymbol(Button button) {
        if (playerTurn % 2 == 0) {
            button.setText("X");
            playerTurn = 1;
        } else {
            button.setText("O");
            playerTurn = 0;
        }
    }


    public boolean checkIfGameIsOver() {
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            if (line.equals("XXX")) {
                tittleText.setText("X wygrał!");
                return true;
            } else if (line.equals("OOO")) {
                tittleText.setText("O wygrało!");
                return true;
            }
            return false;
        }
        return false;
    }
}