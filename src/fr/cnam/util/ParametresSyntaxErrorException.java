package fr.cnam.util;

public class ParametresSyntaxErrorException extends RuntimeException
{
    public ParametresSyntaxErrorException(String texte)
    {
        super(texte);
    }
}