package com.shakdel;

import net.fabricmc.api.ClientModInitializer;

public class ShakshukaDelightClient implements ClientModInitializer{

	@Override
	public void onInitializeClient() {
		ClientImplManager.registerImpls(ConsoleItemImpl.class);
	}

}
