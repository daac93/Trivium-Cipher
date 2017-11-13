package com.daac.crypto.trivium.ui;

import com.daac.crypto.trivium.pojo.FlipFlop;
import com.daac.crypto.trivium.pojo.Register;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.List;

public class UIRegister {

    private int size;
    private Label [] registerNames;
    private Label [] registerValues;

    public UIRegister(int size)   {
        this.size = size;
        registerNames = new Label[size];
        registerValues = new Label[size];

        for(int i = 0; i < size; i++)   {
            registerNames[i] = new Label(String.valueOf(i+1));
            registerValues[i] = new Label("0");
            registerValues[i].setAlignment(Pos.BASELINE_RIGHT);
        }

    }

    public GridPane buildRegisterPane(GridPane gridPane, int rows, int columns, String title) {
        int lastRowIndex = 2;

        gridPane.add(new Label(title), 0,0, columns, 1);
        gridPane.add(new Label(""), 0, 1, columns, 1);

        addColumnConstraints(gridPane, rows, columns);
        for(int i = 0; i < rows; i++)   {
            for(int j = 0; j < columns; j++)    {
                if(j + (i * columns) < size) {
                    gridPane.add(registerNames[j + (i * columns)], j, lastRowIndex);
                    gridPane.add(registerValues[j + (i * columns)], j, lastRowIndex + 1);
                }
            }

            gridPane.add(new Label(""), 0, lastRowIndex + 2, columns, 1);
            lastRowIndex += 3;
        }

        return gridPane;
    }

    public void updateRegisterPane(Register register)    {
        List<FlipFlop> flipFlops = register.getFlipFlops();
        for(int i = 0; i < flipFlops.size(); i++) {
            registerValues[i].setText(flipFlops.get(i).toString());
        }
    }

    private void addColumnConstraints(GridPane gridPane, int rows, int columns)  {
        ColumnConstraints column;
        double columnWidth = 100d / columns;

        for(int i = 0; i < columns; i++) {
            column = new ColumnConstraints();
            column.setPercentWidth(columnWidth);
            gridPane.getColumnConstraints().add(column);
        }
    }

    public Label[] getRegisterNames() {
        return registerNames;
    }

    public void setRegisterNames(Label[] registerNames) {
        this.registerNames = registerNames;
    }

    public Label[] getRegisterValues() {
        return registerValues;
    }

    public void setRegisterValues(Label[] registerValues) {
        this.registerValues = registerValues;
    }
}
