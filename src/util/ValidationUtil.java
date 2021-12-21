package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static Object validate(LinkedHashMap<JFXTextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {

                     ((JFXTextField)textFieldKey).setUnFocusColor(Color.rgb(255, 0, 0));
                     ((JFXTextField)textFieldKey).setFocusColor(Color.rgb(255, 0, 0));
                }
                return textFieldKey;
            }
            ((JFXTextField)textFieldKey).setUnFocusColor(Color.rgb(0, 255, 0));
            ((JFXTextField)textFieldKey).setFocusColor(Color.rgb(0, 255, 0));

        }
        btn.setDisable(false);
        return true;
    }


    public static Object validate_Text(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-fx-border-color: red ; -fx-border-radius: 10 ; -fx-background-radius: 10");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-border-color: green ;-fx-border-radius: 10 ; -fx-background-radius: 10");

        }
        btn.setDisable(false);
        return true;
    }

    public static Object validate_WithPane(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.getParent().setStyle("-fx-border-color: red; -fx-border-radius: 10");

                }
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: green; -fx-border-radius: 10");

        }
        btn.setDisable(false);
        return true;
    }
}
