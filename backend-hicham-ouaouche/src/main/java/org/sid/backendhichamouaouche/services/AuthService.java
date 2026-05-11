package org.sid.backendhichamouaouche.services;

import org.sid.backendhichamouaouche.dtos.AuthRequest;
import org.sid.backendhichamouaouche.dtos.AuthResponse;
import org.sid.backendhichamouaouche.dtos.RegisterRequest;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    AuthResponse register(RegisterRequest request);
}