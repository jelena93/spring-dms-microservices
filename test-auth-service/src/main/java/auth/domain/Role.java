/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.domain;

/**
 *
 * @author Hachiko
 */
public enum Role {
    ADMIN, //kreira kompaniju, kreira korisnika i dodeljuje korisnika kompaniji
    USER, //kreira procese kompaniji
    UPLOADER //uploaduje dokumenta i unosi deskriptore

}
