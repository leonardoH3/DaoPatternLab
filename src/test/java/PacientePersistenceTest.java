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
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
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
    
    //@Test
    public void TestPrimeraClase(){
        //Paciente nuevo que se registra con más de una consulta
       DaoFactory daof = null;
        try{
            //IMPLEMENTACION DE LAS PRUEBAS
            InputStream input = null;
            input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties"); 
            Properties properties=new Properties();
            properties.load(input);
            daof=DaoFactory.getInstance(properties);
            daof.beginSession();
            DaoPaciente dao = daof.getDaoPaciente();
            Paciente p = new Paciente(1025,"cc","Alberto",java.sql.Date.valueOf("2000-01-01"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = "2011-12-31 00:00:00";
            java.util.Date date = sdf.parse(strDate);   
            java.sql.Date sqlDate = new Date(date.getTime());
            Consulta c= new Consulta(12345654,sqlDate,"Tiene una fuerte gripa");    
            Consulta cc= new Consulta(12345653,sqlDate,"Fiebre muy alta"); 
            Set s = new HashSet<Consulta>();
            s.add(c);           
            s.add(cc);
            p.setConsultas(s);
            dao.save(p);
            Paciente p2=dao.load(1025, "cc");    
            daof.commitTransaction();
            daof.endSession();
            //Assert.assertEquals("No se guardaron las consultas correctamente",1025,p2.getId());   
            //Assert.assertEquals("No se guardo correctamente la consulta",true,p2.getConsultas().contains(c));
            Assert.assertEquals("No se guardo correctamente el paciente nuevo con mas de una consulta","Alberto",p2.getNombre());
        }
        catch (PersistenceException | ParseException | IOException ex) {
            try {
               if (daof != null){
                daof.rollbackTransaction();
                daof.endSession();
                
               }
           } catch (PersistenceException ex1) {
               Logger.getLogger(PacientePersistenceTest.class.getName()).log(Level.SEVERE, null, ex1);
           }
        }
    }
    //@Test
    public void TestSegundaClase(){
        //Paciente nuevo que se registra sin consultas
        DaoFactory daof = null;
        try{
            //IMPLEMENTACION DE LAS PRUEBAS
            InputStream input = null;
            input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties"); 
            Properties properties=new Properties();
            properties.load(input);
            daof=DaoFactory.getInstance(properties);
            daof.beginSession();
            DaoPaciente dao = daof.getDaoPaciente();
            Paciente p = new Paciente(1025,"cc","Alberto",java.sql.Date.valueOf("2000-01-01"));
            dao.save(p);
            Paciente p2=dao.load(1025, "cc");        
            daof.commitTransaction();
            daof.endSession();
            //Assert.assertEquals("No se guardaron las consultas correctamente",1025,p2.getId());   
            //Assert.assertEquals("No se guardo correctamente la consulta",true,p2.getConsultas().contains(c));
            Assert.assertEquals("No se guardo correctamente el paciente nuevo con mas de una consulta","Alberto",p2.getNombre());
        }
        catch (PersistenceException | IOException ex) {
           try {
               if (daof != null){
                daof.rollbackTransaction();
                daof.endSession();
               }
           } catch (PersistenceException ex1) {
               Logger.getLogger(PacientePersistenceTest.class.getName()).log(Level.SEVERE, null, ex1);
           } 
        }
    }
    
   
    //@Test
    public void TestTerceraClase(){
        //Paciente nuevo que se registra con sólo una consulta
        DaoFactory daof = null;
        try{
            //IMPLEMENTACION DE LAS PRUEBAS
            InputStream input = null;
            input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties"); 
            Properties properties=new Properties();
            properties.load(input);
            daof=DaoFactory.getInstance(properties);
            daof.beginSession();
            DaoPaciente dao = daof.getDaoPaciente();
            Paciente p = new Paciente(1025,"cc","Alberto",java.sql.Date.valueOf("2000-01-01"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = "2011-12-31 00:00:00";
            java.util.Date date = sdf.parse(strDate);   
            java.sql.Date sqlDate = new Date(date.getTime());
            Consulta c= new Consulta(12345654,sqlDate,"Tiene una fuerte gripa");    
            Set s = new HashSet<Consulta>();
            s.add(c);           
            p.setConsultas(s);
            dao.save(p);
            Paciente p2=dao.load(1025, "cc");
            daof.commitTransaction();
            daof.endSession();
            //Assert.assertEquals("No se guardo correctamente el paciente nuevo con mas de una consulta","Alberto",p2.getNombre());
            Assert.assertEquals("No se guardaron las consultas correctamente",1026,p2.getId());   
            //Assert.assertEquals("No se guardo correctamente la consulta",true,p2.getConsultas().contains(c));    
        }
        catch (PersistenceException | ParseException | IOException ex) {
           try {
               if (daof != null){
                daof.rollbackTransaction();
                daof.endSession();
               }
           } catch (PersistenceException ex1) {
               Logger.getLogger(PacientePersistenceTest.class.getName()).log(Level.SEVERE, null, ex1);
           }
        }
    }
    @Test
    public void TestCuartaClase(){
        //Paciente nuevo que se registra con más de una consulta
       DaoFactory daof = null;
        try{
            //IMPLEMENTACION DE LAS PRUEBAS
            InputStream input = null;
            input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties"); 
            Properties properties=new Properties();
            properties.load(input);
            daof=DaoFactory.getInstance(properties);
            daof.beginSession();
            DaoPaciente dao = daof.getDaoPaciente();
            Paciente p = new Paciente(1025,"cc","Alberto",java.sql.Date.valueOf("2000-01-01"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = "2011-12-31 00:00:00";
            java.util.Date date = sdf.parse(strDate);   
            java.sql.Date sqlDate = new Date(date.getTime());
            Consulta c= new Consulta(12345654,sqlDate,"Tiene una fuerte gripa");    
            Consulta cc= new Consulta(12345653,sqlDate,"Fiebre muy alta"); 
            Set s = new HashSet<Consulta>();
            s.add(c);           
            s.add(cc);
            p.setConsultas(s);
            dao.save(p);
            Paciente p2=dao.load(1025, "cc");    
            daof.commitTransaction();
            daof.endSession();
            //Assert.assertEquals("No se guardaron las consultas correctamente",1025,p2.getId());   
            //Assert.assertEquals("No se guardo correctamente la consulta",true,p2.getConsultas().contains(c));
            Assert.assertEquals("No se guardo correctamente el paciente nuevo con mas de una consulta","Alberto",p2.getNombre());
        }
        catch (PersistenceException | ParseException | IOException ex) {
            try {
               if (daof != null){
                daof.rollbackTransaction();
                daof.endSession();
                
               }
           } catch (PersistenceException ex1) {
               Logger.getLogger(PacientePersistenceTest.class.getName()).log(Level.SEVERE, null, ex1);
           }
        }
    }
}
