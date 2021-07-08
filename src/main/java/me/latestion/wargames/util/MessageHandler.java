package me.latestion.wargames.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class MessageHandler {

    private final TextComponent component;

    public MessageHandler(String text, boolean bold) {
        component = new TextComponent();
        component.setText(text);
        component.setBold(bold);
    }

    public MessageHandler(String text) {
        this(text, false);
    }

    public MessageHandler setColor(ChatColor color) {
        component.setColor(color);
        return this;
    }

    public MessageHandler addExtra(BaseComponent base) {
        component.addExtra(base);
        return this;
    }

    public MessageHandler setClickEvent(String command) {
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return this;
    }

    public MessageHandler setCustomEvent(ClickEvent event) {
        component.setClickEvent(event);
        return this;
    }

    public MessageHandler setHoverEvent(String text) {
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(text)));
        return this;
    }

    public TextComponent getComponent() {
        return component;
    }

}
