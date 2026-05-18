package com.hotel.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapeavel {

    void mapear(ResultSet rs) throws SQLException;
}
