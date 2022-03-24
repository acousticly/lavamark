package com.frederikam.lavamark;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackState;

public class Player extends AudioEventAdapter {

    private AudioPlayer player = Lavamark.PLAYER_MANAGER.createPlayer();

    Player() {
        player.addListener(this);
        player.playTrack(Lavamark.getTrack());

        AudioConsumer consumer = new AudioConsumer(this);
        consumer.start();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        player.playTrack(Lavamark.getTrack());
    }

    boolean canProvide() {
        AudioTrackState state;
        try {
            state = player.getPlayingTrack().getState();
        } catch (NullPointerException ex) {
            player.playTrack(Lavamark.getTrack());
            return false;
        }

        if (state == AudioTrackState.LOADING || state == AudioTrackState.INACTIVE) {
            return true;
        }

        return player.provide() != null;
    }
}
