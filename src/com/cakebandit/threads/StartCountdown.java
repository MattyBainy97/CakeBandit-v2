package com.cakebandit.threads;

import com.cakebandit.GameState;
import com.cakebandit.handlers.Game;
import com.cakebandit.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class StartCountdown implements Runnable {

    private static int timeUntilStart;
    public static boolean forceStart;

    @Override
    public void run() {
        while (true) {
            if (GameState.isState(GameState.IN_LOBBY)) {
                timeUntilStart = 60;
                forceStart = false;
                for (; timeUntilStart >= 0; timeUntilStart--) {

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.setLevel(timeUntilStart);
                    }

                    if (timeUntilStart == 0 || forceStart == true) {
                        if (Game.canStart() == false) {
                            ChatUtilities.broadcast("Not enough players! Countdown restarting!");
                            break;
                        } else {
                            for (Player p : Bukkit.getOnlinePlayers()) {

                                p.playNote(p.getLocation(), Instrument.PIANO, new Note(1, Tone.F, false));

                            }
                            Game.start();
                            break;
                        }
                    }

                    if (timeUntilStart % 10 == 0) {

                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilStart + ChatColor.GOLD + " seconds until the game starts!");

                    }

                    if (timeUntilStart < 4) {
                        ChatUtilities.broadcast(ChatColor.YELLOW + "" + timeUntilStart + ChatColor.GOLD + " seconds until the game starts!");
                        for (Player p : Bukkit.getOnlinePlayers()) {

                            p.playNote(p.getLocation(), Instrument.PIANO, new Note(0, Tone.A, false));

                        }
                    }


                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

    }
}
