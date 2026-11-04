// Last edited by Lucas (November 3rd, 2026)

package ca.accessarchitects.cmpt276group2;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.Console;

public class NavbarView extends LinearLayout {
    public NavbarView(Context context) {
        super(context);
        inflate();
    }

    public NavbarView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        inflate();
    }

    private void inflate()
    {
        Context context = getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.navbar, this);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        Button calendarButton = findViewById(R.id.buttonCalendar);
        Button mainButton = findViewById(R.id.buttonMain);
        Button settingsButton = findViewById(R.id.buttonSettings);

        /* TODO:
            Uncomment the relevant intent and replace '<class>' with your activity.
            Also uncomment the startActivity() call in the onClickListener below.
            - Lucas
         */
        Context context = this.getContext();
        // Intent calendarSwitchIntent = new Intent(context, <class>);
        Intent mainSwitchIntent = new Intent(context, MainActivity.class);
        Intent settingsSwitchIntent = new Intent(context, SettingsActivity.class);

        calendarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // context.startActivity(calendarSwitchIntent);
            }
        });

        mainButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(mainSwitchIntent);
            }
        });

        settingsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(settingsSwitchIntent);
            }
        });
    }
}
