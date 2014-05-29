package com.hkparker.bootmycomputer;

import java.util.concurrent.ExecutionException;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.glass.media.Sounds;
import java.io.IOException;

public class BootActivity extends Activity{
	public String ssh_command = "sudo arp -W 11:22:33:44:55:66 em0";
	
	public boolean send_command() throws IOException {
		AsyncTask<String, Void, Boolean> ssh_exec = new BackgroundSSHExec().execute(this.ssh_command);
		Boolean success = false;
		try {
			success = (Boolean) ssh_exec.get();
		} catch (InterruptedException e) { e.printStackTrace();
		} catch (ExecutionException e) { e.printStackTrace(); }
		return success;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AudioManager audio = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		try {
			if(send_command()){
				audio.playSoundEffect(Sounds.SUCCESS);
			} else {
				audio.playSoundEffect(Sounds.ERROR);
			}
		} catch (IOException e) { }
		finish();
	}
}
