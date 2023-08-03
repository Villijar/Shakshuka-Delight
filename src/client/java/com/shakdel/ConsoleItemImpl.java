package com.shakdel;

import com.shakdel.ClientImplManager.ClientImpl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;

@ClientImpl(ConsoleItem.class)
public class ConsoleItemImpl {
	public static void openConsole(ConsoleItem item) {
		MinecraftClient.getInstance().setScreen(new ChatScreen("/"));
	}
}
