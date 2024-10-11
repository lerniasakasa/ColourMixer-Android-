package com.example.colourmixeer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Spinner color1Spinner, color2Spinner, color3Spinner;
    private EditText color1Proportion, color2Proportion, color3Proportion;
    private View resultColor;
    private Button mixButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing UI components
        color1Spinner = findViewById(R.id.color1Spinner);
        color2Spinner = findViewById(R.id.color2Spinner);
        color3Spinner = findViewById(R.id.color3Spinner);

        color1Proportion = findViewById(R.id.color1Proportion);
        color2Proportion = findViewById(R.id.color2Proportion);
        color3Proportion = findViewById(R.id.color3Proportion);

        resultColor = findViewById(R.id.resultColor);
        mixButton = findViewById(R.id.mixButton);

        // Setting up color options
        String[] colors = {"Red", "Orange", "Yellow", "Blue", "Green", "Purple", "Black", "White"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colors);

        color1Spinner.setAdapter(adapter);
        color2Spinner.setAdapter(adapter);
        color3Spinner.setAdapter(adapter);

        // Mix button onClick listener
        mixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mixColors();
            }
        });
    }

    private void mixColors() {
        try {
            // Get colors and proportions
            String color1 = color1Spinner.getSelectedItem().toString();
            String color2 = color2Spinner.getSelectedItem().toString();
            String color3 = color3Spinner.getSelectedItem().toString();

            float proportion1 = Float.parseFloat(color1Proportion.getText().toString());
            float proportion2 = Float.parseFloat(color2Proportion.getText().toString());
            float proportion3 = Float.parseFloat(color3Proportion.getText().toString());

            // Ensure proportions add up to 100%
            float totalProportion = proportion1 + proportion2 + proportion3;
            if (totalProportion != 100.0f) {
                Toast.makeText(this, "Proportions must add up to 100%", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get RGB values for each color
            int rgb1 = getColorValue(color1);
            int rgb2 = getColorValue(color2);
            int rgb3 = getColorValue(color3);

            // Calculate weighted average of RGB values
            int red = (int) ((Color.red(rgb1) * proportion1 + Color.red(rgb2) * proportion2 + Color.red(rgb3) * proportion3) / 100);
            int green = (int) ((Color.green(rgb1) * proportion1 + Color.green(rgb2) * proportion2 + Color.green(rgb3) * proportion3) / 100);
            int blue = (int) ((Color.blue(rgb1) * proportion1 + Color.blue(rgb2) * proportion2 + Color.blue(rgb3) * proportion3) / 100);

            // Set result color
            resultColor.setBackgroundColor(Color.rgb(red, green, blue));

            // After you calculate the mixedColor
            int mixedColor = Color.rgb(red, green, blue);

// Extract RGB components
            int redFinal = Color.red(mixedColor);
            int greenFinal = Color.green(mixedColor);
            int blueFinal = Color.blue(mixedColor);

// Convert to Hex code
            String hexCode = String.format("#%02X%02X%02X", red, green, blue);

// Set the values in the TextViews
            TextView hexCodeView = findViewById(R.id.hexCodeValue);
            TextView rgbValueView = findViewById(R.id.rgbValue);

            hexCodeView.setText(hexCode);
            rgbValueView.setText("RGB(" + red + ", " + green + ", " + blue + ")");


        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid proportions", Toast.LENGTH_SHORT).show();
        }
    }

    private int getColorValue(String colorName) {
        switch (colorName) {
            case "Red":
                return Color.RED;
            case "Orange":
                return Color.rgb(255, 165, 0);
            case "Yellow":
                return Color.YELLOW;
            case "Blue":
                return Color.BLUE;
            case "Green":
                return Color.GREEN;
            case "Purple":
                return Color.rgb(128, 0, 128);
            case "Black":
                return Color.BLACK;
            case "White":
                return Color.WHITE;
            default:
                return Color.WHITE;
        }
    }
}
