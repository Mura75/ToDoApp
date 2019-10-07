package kz.mobile.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateToDoActivity extends AppCompatActivity implements OnTaskCompleted {

    private EditText inputName;
    private EditText inputDescription;
    private Button buttonCreate;

    private ToDo toDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_to_do);
        initViews();
        setToDoData();
    }

    private void initViews() {
        inputName = findViewById(R.id.inputName);
        inputDescription = findViewById(R.id.inputDescription);
        buttonCreate = findViewById(R.id.buttonCreate);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String description = inputDescription.getText().toString();
                createToDo(name, description);
            }
        });
    }

    private void setToDoData() {
        if (getIntent().hasExtra(Constants.TODO)) {
            //Todo check ToDo entity for null
            toDo = (ToDo)getIntent().getSerializableExtra(Constants.TODO);
            inputName.setText(toDo.getName());
            inputDescription.setText(toDo.getDescription());
        }
    }

    private void createToDo(String name, String description) {
        //TODO check strings for null and empty values
        if (toDo == null) {
            toDo = new ToDo(name, description);
        } else {
            toDo.setName(name);
            toDo.setDescription(description);
        }
        new CreateToDoAsync(this).execute(toDo);
    }

    @Override
    public void itemCreated(ToDo toDo) {
        Intent intent = new Intent();
        intent.putExtra(Constants.TODO, toDo);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private static class CreateToDoAsync extends AsyncTask<ToDo, Void, ToDo> {

        private OnTaskCompleted listener;

        CreateToDoAsync(OnTaskCompleted listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ToDo doInBackground(ToDo... voids) {
            //Todo save object to file
            return voids[0];
        }

        @Override
        protected void onPostExecute(ToDo item) {
            super.onPostExecute(item);
            if (listener != null) {
                listener.itemCreated(item);
            }
        }
    }
}
