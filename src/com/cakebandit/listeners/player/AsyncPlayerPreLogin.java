package com.cakebandit.listeners.player;

import com.cakebandit.GameState;
import com.cakebandit.CakeBandit;
import com.cakebandit.listeners.CBListener;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

public class AsyncPlayerPreLogin extends CBListener {

    public AsyncPlayerPreLogin(CakeBandit pl) {
        super(pl);
    }

    @EventHandler
    public void playerPreLogin(AsyncPlayerPreLoginEvent e) {

        if (GameState.isState(GameState.PRE_GAME)) {

            e.disallow(Result.KICK_OTHER, ChatColor.RED + "A game is starting! Try joining in a few seconds!");

        } else if (GameState.isState(GameState.IN_GAME)) {

            e.allow();

        } else if (GameState.isState(GameState.POST_GAME)) {

            e.disallow(Result.KICK_OTHER, ChatColor.RED + "A game is ending!");

        } else if (GameState.isState(GameState.RESET)) {

            e.disallow(Result.KICK_OTHER, ChatColor.RED + "The server is restarting!");

        } else {

            e.allow();

        }

    }
}