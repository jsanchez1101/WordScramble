package com.example.wordscramblegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView scrambledWordTextView;
    private EditText guessEditText;
    private Button submitGuessButton;
    private TextView definitionTextView;
    private ProgressBar loadingProgressBar;
    private Button newWordButton;
    private Button giveUpButton; // New Button

    private ImageView image1, image2, image3; // ImageViews

    private WordApi wordApi;
    private DictionaryApi dictionaryApi;

    private String originalWord;
    private int attemptCount = 0;
    private final int MAX_ATTEMPTS = 3; // Maximum attempts allowed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        scrambledWordTextView = findViewById(R.id.scrambledWordTextView);
        guessEditText = findViewById(R.id.guessEditText);
        submitGuessButton = findViewById(R.id.submitGuessButton);
        definitionTextView = findViewById(R.id.definitionTextView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        newWordButton = findViewById(R.id.newWordButton);
        giveUpButton = findViewById(R.id.giveUpButton); // Initialize "Give Up" Button

        // Initialize ImageViews
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        // Initialize Retrofit APIs
        wordApi = com.example.scrabblegameapp.ApiClient.getRandomWordClient().create(WordApi.class);
        dictionaryApi = com.example.scrabblegameapp.ApiClient.getDictionaryClient().create(DictionaryApi.class);

        // Fetch the first word
        fetchNewScrambledWord();

        // Set click listener for Submit Guess button
        submitGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userGuess = guessEditText.getText().toString().trim().toLowerCase();
                if (!userGuess.isEmpty()) {
                    if (userGuess.equals(originalWord.toLowerCase())) {
                        Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                        fetchWordDefinition(originalWord);
                        submitGuessButton.setEnabled(false);
                        giveUpButton.setVisibility(View.GONE); // Hide "Give Up" button upon correct guess
                        newWordButton.setVisibility(View.VISIBLE);
                    } else {
                        attemptCount++;
                        if (attemptCount >= MAX_ATTEMPTS) {
                            Toast.makeText(MainActivity.this, "Maximum attempts reached!", Toast.LENGTH_SHORT).show();
                            definitionTextView.setText("The correct word was: " + originalWord);
                            submitGuessButton.setEnabled(false);
                            giveUpButton.setVisibility(View.GONE);
                            newWordButton.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(MainActivity.this, "Incorrect. Try again!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Attempts left: " + (MAX_ATTEMPTS - attemptCount), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter your guess.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for Give Up button
        giveUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                definitionTextView.setText("The correct word was: " + originalWord);
                submitGuessButton.setEnabled(false);
                giveUpButton.setVisibility(View.GONE);
                newWordButton.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Word revealed. Click 'New Word' to continue.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for New Word button
        newWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchNewScrambledWord();
                definitionTextView.setText("");
                guessEditText.setText("");
                submitGuessButton.setEnabled(true);
                newWordButton.setVisibility(View.GONE);
                giveUpButton.setVisibility(View.VISIBLE);
                attemptCount = 0; // Reset attempts
            }
        });
    }

    /**
     * Fetches a random word from the Random Words API and displays its scrambled version.
     */
    private void fetchNewScrambledWord() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        scrambledWordTextView.setText("Loading...");
        definitionTextView.setText("");

        Call<List<String>> call = wordApi.getRandomWord(1);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                loadingProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    originalWord = response.body().get(0);
                    String scrambled = scrambleWord(originalWord);
                    scrambledWordTextView.setText(scrambled);
                    Log.d(TAG, "Original Word: " + originalWord + ", Scrambled: " + scrambled);

                    // Set default images
                    setDefaultImages();
                } else {
                    scrambledWordTextView.setText("Failed to load word.");
                    Toast.makeText(MainActivity.this, "Failed to load word. Try again.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Random Word API Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
                scrambledWordTextView.setText("Error loading word.");
                Toast.makeText(MainActivity.this, "Error loading word.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Random Word API Error: " + t.getMessage());
            }
        });
    }

    /**
     * Scrambles the letters of the given word.
     *
     * @param word The original word.
     * @return The scrambled word.
     */
    private String scrambleWord(String word) {
        List<Character> characters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);
        StringBuilder scrambled = new StringBuilder();
        for (char c : characters) {
            scrambled.append(c);
        }
        // Ensure the scrambled word is different from the original
        if (scrambled.toString().equalsIgnoreCase(word)) {
            return scrambleWord(word);
        }
        return scrambled.toString();
    }

    /**
     * Fetches the definition of the given word from the Dictionary API.
     *
     * @param word The word to define.
     */
    private void fetchWordDefinition(String word) {
        loadingProgressBar.setVisibility(View.VISIBLE);
        definitionTextView.setText("Fetching definition...");

        Call<List<DictionaryResponse>> call = dictionaryApi.getWordDefinition(word);
        call.enqueue(new Callback<List<DictionaryResponse>>() {
            @Override
            public void onResponse(Call<List<DictionaryResponse>> call, Response<List<DictionaryResponse>> response) {
                loadingProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    DictionaryResponse dictionaryResponse = response.body().get(0);
                    if (dictionaryResponse.getMeanings() != null && !dictionaryResponse.getMeanings().isEmpty()) {
                        DictionaryResponse.Meaning meaning = dictionaryResponse.getMeanings().get(0);
                        if (meaning.getDefinitions() != null && !meaning.getDefinitions().isEmpty()) {
                            String definition = meaning.getDefinitions().get(0).getDefinition();
                            definitionTextView.setText("Definition: " + definition);
                        } else {
                            definitionTextView.setText("No definition available.");
                        }
                    } else {
                        definitionTextView.setText("No definition available.");
                    }
                } else {
                    definitionTextView.setText("Definition not found.");
                    Log.e(TAG, "Dictionary API Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<DictionaryResponse>> call, Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
                definitionTextView.setText("Error fetching definition.");
                Toast.makeText(MainActivity.this, "Error fetching definition.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Dictionary API Error: " + t.getMessage());
            }
        });
    }

    /**
     * Sets default images for all ImageViews.
     */
    private void setDefaultImages() {
        image1.setImageResource(R.drawable.the);
        image2.setImageResource(R.drawable.scrabble);
        image3.setImageResource(R.drawable.game);
    }
}