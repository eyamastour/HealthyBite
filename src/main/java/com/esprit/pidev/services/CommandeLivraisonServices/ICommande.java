package com.esprit.pidev.services.CommandeLivraisonServices;

import com.esprit.pidev.entities.CommandeLivraison.Commande;
import com.esprit.pidev.entities.CommandeLivraison.EtatCommande;
import com.esprit.pidev.entities.ProduitRepas.Repas;


import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;


        public interface ICommande {
                Commande addCommande(Commande commande);
                Commande updateCommande(Commande commande);
                void deleteCommandeById(Long id);
                Commande getCommandeById(Long id);
                List<Commande> getAllCommandes();
                List<Commande> getCommandesByEtat(EtatCommande etatCommande);


                List<Commande> findCommandesByDateAndEtat(Date startDate, Date endDate, EtatCommande etatCommande);



        }