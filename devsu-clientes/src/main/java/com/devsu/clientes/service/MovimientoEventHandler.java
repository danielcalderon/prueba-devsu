package com.devsu.clientes.service;

import com.devsu.clientes.event.MovimientoEvent;

public interface MovimientoEventHandler {

    void handleMovimientoRealizado(MovimientoEvent movimientoEvent);
}
