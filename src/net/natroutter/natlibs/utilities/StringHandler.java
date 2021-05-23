package net.natroutter.natlibs.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StringHandler {

	String value = "";
	String prefix = "";
	String suffix = "";

	public StringHandler() {}

	public StringHandler(Object value) {
		this.value = value.toString();
	}

	public StringHandler(List<String> list, Character separator) {
		this.value = String.join(separator.toString(), list);
	}
	public StringHandler(String[] list, Character separator) {
		this.value = String.join(separator.toString(), list);
	}

	public StringHandler(List<String> list, String separator) {
		this.value = String.join(separator, list);
	}
	public StringHandler(String[] list, String separator) {
		this.value = String.join(separator, list);
	}

	public StringHandler(Collection<?> list, Character separator) {
		List<String> strList = new ArrayList<String>();
		for (Object ob : list) {
			strList.add(ob.toString());
		}
		this.value = String.join(separator.toString(), strList);
	}
	
	public static StringHandler from(StringHandler OriginalHandler, String value) {
		StringHandler handler = new StringHandler(value);
		handler.setPrefix(OriginalHandler.prefix);
		handler.setPrefix(OriginalHandler.suffix);
		return handler;
	}

	public void setValue(Object value) {
		this.value = value.toString();
	}
	
	public StringHandler replace(Object oldValue, Object newValue) {
		if (newValue instanceof StringHandler) {
			newValue = ((StringHandler) newValue).build();
		}
		value = value.replace(oldValue.toString(), newValue.toString());
		return this;
	}
	
	public StringHandler replaceAll(Object oldValue, Object newValue) {
		if (newValue instanceof StringHandler) {
			newValue = ((StringHandler) newValue).build();
		}
		value = value.replaceAll(Pattern.quote(oldValue.toString()), newValue.toString());
		return this;
	}
	
	public StringHandler setPrefix(Object prefix) {
		if (prefix instanceof StringHandler) {
			prefix = ((StringHandler) prefix).build();
		}
		this.prefix = prefix.toString();
		return this;
	}
	
	public StringHandler setSuffix(Object suffix) {
		if (suffix instanceof StringHandler) {
			suffix = ((StringHandler) suffix).build();
		}
		this.suffix = suffix.toString();
		return this;
	}
	
	public StringHandler addToStart(Object start) {
		if (start instanceof StringHandler) {
			start = ((StringHandler) start).build();
		}
		value = start.toString() + value;
		return this;
	}
	
	public StringHandler addToEnd(Object end) {
		if (end instanceof StringHandler) {
			end = ((StringHandler) end).build();
		}
		value = value + end.toString();
		return this;
	}
	
	public StringHandler replaceColors() {
		value = ChatColor.translateAlternateColorCodes('&', value);
		return this;
	}
	
	public StringHandler stripColors() {
		value = ChatColor.stripColor(value);
		return this;
	}

	public StringHandler compine(StringHandler handler) {
		return new StringHandler(this.build()).addToEnd(handler.build());
	}
	
	public List<String> split(Character ch) {
		return Arrays.asList(value.split(Pattern.quote(ch.toString())));
	}
	
	public String build() {
		return prefix + value + suffix;
	}
	
	public void send(Player p) {
		p.sendMessage(this.build());
	}

	public void broadcast() {
		Bukkit.broadcastMessage(build());
	}
	
}
