/*
 * Copyright (C) 2016 hcadavid
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

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class PacientePersistenceTest {
    
    public PacientePersistenceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void databaseConnectionTest() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
        
        daof.beginSession();
        
        //IMPLEMENTACION DE LAS PRUEBAS

        daof.commitTransaction();
        daof.endSession();        
    }
    
    @Test
    public void TestPrimeraClase() throws IOException, PersistenceException{
        //Paciente nuevo que se registra con más de una consulta
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties"); 
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        try{
            //IMPLEMENTACION DE LAS PRUEBAS
            Paciente p = new Paciente(1023,"cc","Alberto",java.sql.Date.valueOf("2000-01-01"));
            Consulta c= new Consulta();    
            Consulta cc= new Consulta(); 
            Set s = new HashSet<Consulta>();
            s.add(c);           
            s.add(cc);
            p.setConsultas(s);
            daof.getDaoPaciente().save(p);
            daof.commitTransaction();
            daof.endSession();
        }
        catch (PersistenceException ex) {
            daof.rollbackTransaction();
            daof.endSession();
            Logger.getLogger(PacientePersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public void TestSegundaClase() throws IOException, PersistenceException{
        //Paciente nuevo que se registra sin consultas
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties"); 
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        try{
            //IMPLEMENTACION DE LAS PRUEBAS
            Paciente p = new Paciente(1023,"cc","Alberto",java.sql.Date.valueOf("2000-01-01"));           
            daof.getDaoPaciente().save(p);
            daof.commitTransaction();
            daof.endSession();
        }
        catch (PersistenceException ex) {
            daof.rollbackTransaction();
            daof.endSession();
            Logger.getLogger(PacientePersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
   
    
    public void TestTerceraClase() throws IOException, PersistenceException{
        //Paciente nuevo que se registra con sólo una consulta
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties"); 
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        try{
            //IMPLEMENTACION DE LAS PRUEBAS
            Paciente p = new Paciente(1023,"cc","Alberto",java.sql.Date.valueOf("2000-01-01"));
            Consulta c= new Consulta();            
            Set s = new HashSet<Consulta>();
            s.add(c);           
            p.setConsultas(s);
            daof.getDaoPaciente().save(p);
            daof.commitTransaction();
            daof.endSession();
        }
        catch (PersistenceException ex) {
            daof.rollbackTransaction();
            daof.endSession();
            Logger.getLogger(PacientePersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public void TestCuartaClase() throws IOException, PersistenceException{
        //Paciente nuevo YA existente que se registra con más de una consulta
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties"); 
        Properties properties=new Properties();
        properties.load(input);
        DaoFactory daof=DaoFactory.getInstance(properties);
        daof.beginSession();
        try{
            //IMPLEMENTACION DE LAS PRUEBAS
            Paciente p = new Paciente(1023,"cc","Alberto",java.sql.Date.valueOf("2000-01-01"));
            Consulta c= new Consulta();
            Consulta cc= new Consulta();
            Set s = new HashSet<Consulta>();
            s.add(c);
            s.add(cc);
            p.setConsultas(s);            
            daof.getDaoPaciente().save(p);
            daof.commitTransaction();
            daof.getDaoPaciente().save(p);
            daof.commitTransaction();
            daof.endSession();
        }
        catch (PersistenceException ex) {
            daof.rollbackTransaction();
            daof.endSession();
            Logger.getLogger(PacientePersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
