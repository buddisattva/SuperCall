package com.fakecall;


import net.emome.hamiapps.sdk.ForwardActivity;

public class CheckLicense extends ForwardActivity
{
	@SuppressWarnings("unchecked")
	@Override
	public Class getTargetActivity() 
	{
		return abcActivity.class;
	}
	
	@Override
	public boolean passOnUnavailableDataNetwork()
	{
		return true;
	}
}