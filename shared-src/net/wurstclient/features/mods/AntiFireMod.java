/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.features.mods;

import net.minecraft.network.play.client.CPacketPlayer;
import net.wurstclient.compatibility.WConnection;
import net.wurstclient.compatibility.WMinecraft;
import net.wurstclient.events.listeners.UpdateListener;
import net.wurstclient.features.Mod;
import net.wurstclient.features.SearchTags;

@SearchTags({"anti fire", "AntiBurn", "anti burn", "NoFire", "no fire"})
@Mod.Info(help = "Mods/AntiFire")
@Mod.Bypasses(ghostMode = false, latestNCP = false, olderNCP = false)
public final class AntiFireMod extends Mod implements UpdateListener
{
	public AntiFireMod()
	{
		super("AntiFire",
			"Blocks damage from catching on fire.\n"
				+ "Does NOT block damage from standing inside of fire.\n"
				+ "Requires a full hunger bar.");
	}
	
	@Override
	public void onEnable()
	{
		wurst.events.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		// check gamemode
		if(WMinecraft.getPlayer().capabilities.isCreativeMode)
			return;
		
		// check onGround
		if(!WMinecraft.getPlayer().onGround)
			return;
		
		// check if burning
		if(!WMinecraft.getPlayer().isBurning())
			return;
		
		// send updates
		for(int i = 0; i < 100; i++)
			WConnection.sendPacket(new CPacketPlayer());
	}
}
