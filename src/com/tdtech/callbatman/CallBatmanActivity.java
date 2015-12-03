package com.tdtech.callbatman;

import java.util.Random;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class CallBatmanActivity extends Activity implements OnClickListener {
    
    private static final int[] BATMAN_CALLS_RES = {
        R.raw.batman1,
        R.raw.batman2
    };
    
    private final int[] mBatmanCalls = new int[BATMAN_CALLS_RES.length];
    private final Random mRandom = new Random();
    
    private SoundPool mSoundPool;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_batman_activity);
        
        mSoundPool = buildSoundPool();
        
        for (int i = 0; i < BATMAN_CALLS_RES.length; i++) {
            mBatmanCalls[i] = mSoundPool.load(this, BATMAN_CALLS_RES[i], 1);
        }
        
        findViewById(R.id.cb_call_batman_button).setOnClickListener(this);
    }
    
    @Override
    protected void onDestroy() {
        for (int i = 0; i < BATMAN_CALLS_RES.length; i++) {
            mSoundPool.unload(mBatmanCalls[i]);
        }
        
        mSoundPool.release();
        super.onDestroy();
    }
    
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool buildSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes.Builder attributesBuilder = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME);
            
            SoundPool.Builder builder = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(attributesBuilder.build());
            
            return builder.build();
        }
        
        return new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cb_call_batman_button) {
            mSoundPool.play(mBatmanCalls[mRandom.nextInt(2)], 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }
}