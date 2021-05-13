package com.hexagonaldemo.ticketapi.common.commandhandler;

import com.hexagonaldemo.ticketapi.common.model.Command;

public interface CommandHandler<E, T extends Command> {

    E handle(T command);
}
