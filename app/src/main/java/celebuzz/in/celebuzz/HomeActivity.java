package celebuzz.in.celebuzz;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.droidparts.widget.ClearableEditText;

public class HomeActivity extends Activity {

    private View mHomeView, mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // load custom toolbar
        setCustomToolBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setCustomToolBar() {

        final ActionBar actionBar = getActionBar();

        mHomeView = View.inflate(this, R.layout.actionbar_homeview, null);
        mSearchView = View.inflate(this, R.layout.actionbar_searchview, null);

        // Configure event listeners for homeview action bar elements
        ImageButton search = (ImageButton) mHomeView.findViewById(R.id.imageButton_Search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Search Triggered..", Toast.LENGTH_LONG).show();
                actionBar.setCustomView(mSearchView, new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });


        // Configure event listeners for searchview action bar elements
        ImageButton back = (ImageButton) mSearchView.findViewById(R.id.imageButton_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Back Triggered..", Toast.LENGTH_LONG).show();
                actionBar.setCustomView(mHomeView, new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });

        final ClearableEditText searchText = (ClearableEditText) mSearchView.findViewById(R.id.editText_search);

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    searchContentForSearchText(searchText.getText().toString().trim());
                    handled = true;
                }
                return handled;
            }
        });

        TextWatcher tw = new TextWatcher() {
            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // you can check for enter key here
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // do something with search text
            }
        };

        searchText.addTextChangedListener(tw);

        // initially set to show homeview in actionbar
        // We need to explicitly specify the layout params for actionbar, otherwise custom layout won't render properly
        // http://stackoverflow.com/questions/11264808/android-action-bar-with-two-stretched-buttons
        actionBar.setCustomView(mHomeView, new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

    }

    private void searchContentForSearchText(String searchText) {
        if (searchText.length() > 0) {
            Log.i("INFO", "search message for " + searchText);
            View homeView = this.findViewById(R.id.layout_homeview);
            TextView tv = (TextView) homeView.findViewById(R.id.homeview_textcontainer);
            tv.setText(searchText + " - No search result found!!");
        }
    }
}


/*

External Libs:
1. https://github.com/yanchenko/droidparts/tree/develop for
    - using ClearableEditText widget

 */
