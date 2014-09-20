package com.kinwa91.triggar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;


public class NewProfileActivity extends Activity {

    // EditText
    private EditText profileNameEditText;

    // CheckBoxes:Trigger
    private CheckBox airplaneTriggerCheckbox;
    private CheckBox wifiTriggerCheckbox;
    private CheckBox bluetoothTriggerCheckbox;
    private CheckBox batteryTriggerCheckbox;
    private CheckBox headsetTriggerCheckbox;
    private CheckBox powerTriggerCheckbox;

    // CheckBoxes:Action
    private CheckBox alarmActionCheckbox;
    private CheckBox bluetoothActionCheckbox;
    private CheckBox brightnessActionCheckbox;
    private CheckBox musicActionCheckbox;
    private CheckBox volumeActionCheckbox;
    private CheckBox wifiActionCheckbox;

    // Switches:Trigger
    private Switch airplaneTriggerSwitch;
    private Switch wifiTriggerSwitch;
    private Switch bluetoothTriggerSwitch;
    private Switch headsetTriggerSwitch;
    private Switch powerTriggerSwitch;

    // Switches:Action
    private Switch bluetoothActionSwitch;
    private Switch musicActionSwitch;
    private Switch wifiActionSwitch;

    // Seekbar:Action
    private SeekBar brightnessSeekbar;
    private SeekBar volumeSeekbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);
        bindViews();
        setTitle(getString(R.string.new_profile));
    }

    private void bindViews() {

        profileNameEditText = (EditText) findViewById(R.id.profile_name_edittext);

        airplaneTriggerCheckbox = (CheckBox) findViewById(R.id.airplane_checkbox);
        wifiTriggerCheckbox = (CheckBox) findViewById(R.id.wifi_checkbox);
        bluetoothTriggerCheckbox = (CheckBox) findViewById(R.id.bluetooth_checkbox);
        batteryTriggerCheckbox = (CheckBox) findViewById(R.id.battery_checkbox);
        headsetTriggerCheckbox = (CheckBox) findViewById(R.id.headset_checkbox);
        powerTriggerCheckbox = (CheckBox) findViewById(R.id.power_checkbox);

        alarmActionCheckbox = (CheckBox) findViewById(R.id.alarm_action_checkbox);
        bluetoothActionCheckbox = (CheckBox) findViewById(R.id.bluetooth_action_checkbox);
        brightnessActionCheckbox = (CheckBox) findViewById(R.id.brightness_action_checkbox);
        musicActionCheckbox = (CheckBox) findViewById(R.id.music_action_checkbox);
        volumeActionCheckbox = (CheckBox) findViewById(R.id.volume_action_checkbox);
        wifiActionCheckbox = (CheckBox) findViewById(R.id.wifi_action_checkbox);

        airplaneTriggerSwitch = (Switch) findViewById(R.id.airplane_switch);
        wifiTriggerSwitch = (Switch) findViewById(R.id.wifi_switch);
        bluetoothTriggerSwitch = (Switch) findViewById(R.id.bluetooth_switch);
        headsetTriggerSwitch = (Switch) findViewById(R.id.headset_switch);
        powerTriggerSwitch = (Switch) findViewById(R.id.power_switch);

        bluetoothActionSwitch = (Switch) findViewById(R.id.bluetooth_action_switch);
        musicActionSwitch = (Switch) findViewById(R.id.music_action_switch);
        wifiActionSwitch = (Switch) findViewById(R.id.wifi_action_switch);

        brightnessSeekbar = (SeekBar) findViewById(R.id.brightness_seekbar);
        volumeSeekbar = (SeekBar) findViewById(R.id.volume_seekbar);

        brightnessSeekbar.setMax(100);
        volumeSeekbar.setMax(100);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_new_profile_create) {
            createProfile();
            return true;
        } else if (id == R.id.action_new_profile_cancel) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createProfile() {
        ProfileDbExchanger db = new ProfileDbExchanger(getApplicationContext());
        db.open();
        int profileId = db.createProfile(profileNameEditText.getText().toString(), 0);
        if (airplaneTriggerCheckbox.isChecked())
            db.createTrigger("Airplane", airplaneTriggerSwitch.isChecked() ? 1 : 0, profileId);
        if (wifiTriggerCheckbox.isChecked())
            db.createTrigger("Wifi", wifiTriggerSwitch.isChecked() ? 1 : 0, profileId);
        if (bluetoothTriggerCheckbox.isChecked())
            db.createTrigger("Bluetooth", bluetoothTriggerSwitch.isChecked() ? 1 : 0, profileId);
        if (batteryTriggerCheckbox.isChecked())
            db.createTrigger("Battery", 1, profileId);
        if (headsetTriggerCheckbox.isChecked())
            db.createTrigger("Headset", headsetTriggerSwitch.isChecked() ? 1 : 0, profileId);
        if (powerTriggerCheckbox.isChecked())
            db.createTrigger("Power", powerTriggerSwitch.isChecked() ? 1 : 0, profileId);

        if (alarmActionCheckbox.isChecked())
            db.createAction("Alarm", 1, profileId);
        if (bluetoothActionCheckbox.isChecked())
            db.createAction("Bluetooth", bluetoothActionSwitch.isChecked() ? 1 : 0, profileId);
        if (brightnessActionCheckbox.isChecked())
            db.createAction("Brightness", brightnessSeekbar.getProgress(), profileId);
        if (musicActionCheckbox.isChecked())
            db.createAction("Music", musicActionSwitch.isChecked() ? 1 : 0, profileId);
        if (volumeActionCheckbox.isChecked())
            db.createAction("Volume", volumeSeekbar.getProgress(), profileId);
        if (wifiActionCheckbox.isChecked())
            db.createAction("Wifi", wifiActionSwitch.isChecked() ? 1 : 0, profileId);

        db.close();

        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
