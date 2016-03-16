/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.persistence.jdbcimpl;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoPaciente implements DaoPaciente {

    Connection con;

    public JDBCDaoPaciente(Connection con) {
        this.con = con;
    }
        

    @Override
    public Paciente load(int idpaciente, String tipoid) throws PersistenceException {
        Paciente pa=null;
        PreparedStatement ps;
        ResultSet resultado;
        String inputString ="select pac.nombre, pac.fecha_nacimiento, con.idCONSULTAS, con.fecha_y_hora, con.resumen from PACIENTES as pac inner join CONSULTAS as con on con.PACIENTES_id=pac.id and con.PACIENTES_tipo_id=pac.tipo_id where pac.id=? and pac.tipo_id=?";
        try {
            ps = con.prepareStatement(inputString);
            ps.setInt(1, idpaciente);
            ps.setString(2, tipoid);      
            resultado=ps.executeQuery();
            while(resultado.next()){
                if(resultado.isFirst()){
                    pa = new Paciente(idpaciente,tipoid,resultado.getString(1),resultado.getDate(2));
                }
                Array consultasId = resultado.getArray(3);
                Array consultasFecha = resultado.getArray(4);
                Array consultasResumen = resultado.getArray(5);
                int[] consultasIdTwo = (int[])consultasId.getArray();
                Date[] consultasFechaTwo = (Date[])consultasFecha.getArray();
                String[] consultasResumenTwo = (String[])consultasId.getArray();
                Set s = new HashSet<Consulta>();
                for (int i = 0; i<consultasIdTwo.length;i++){
                    Consulta c = new Consulta(consultasIdTwo[i],consultasFechaTwo[i],consultasResumenTwo[i]);
                    s.add(c);
                }
            pa.setConsultas(s);
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading "+idpaciente,ex);
        }
        return pa;
    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        String inputString ="INSERT INTO PACIENTES VALUES(?,?,?,?)";
        String inputStringTwo = "INSERT INTO CONSULTAS VALUES(?,?,?,?,?)";
        try {
            ps = con.prepareStatement(inputString);
            ps.setInt(1, p.getId()); 
            ps.setString(2, p.getTipo_id());    
            ps.setString(3, p.getNombre());    
            ps.setDate(4, p.getFechaNacimiento());    
            ps.execute(); 
            for(Consulta c:p.getConsultas()){
                ps = con.prepareStatement(inputStringTwo);
                ps.setInt(1,c.getId());
                ps.setDate(2,c.getFechayHora());
                ps.setString(3,c.getResumen());
                ps.setInt(4,p.getId());
                ps.setString(5,p.getTipo_id());
                ps.execute(); 
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        }
    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        /*try {
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        } */
        throw new RuntimeException("No se ha implementado el metodo 'load' del DAOPAcienteJDBC");
    }
    
}
