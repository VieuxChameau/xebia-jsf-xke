<<<<<<< HEAD
delete from SLOT;
insert into SLOT (id, title, description, videoAccepted, scheduleDate, duration, author_id, slotType) values (1, 'JSF 2 en action', STRINGDECODE('Utilisation de JSF2 pour cr\u00E9er une web app, accompagn\u00E9e de PrettyFaces (Friendly Url, Navigation), OmniFaces (Utils) et PrimeFaces (Components)...\n\nUn socle de base sera fourni et il sera demand\u00E9 aux participants d''ajouter des fonctionnalit\u00E9s de mani\u00E8re progressive. Diff\u00E9rentes solutions seront propos\u00E9es concernant certaines probl\u00E9matiques.'), FALSE, DATE '2013-01-01', '2 h', NULL, 5);

delete from USER;
insert into USER(id, firstName, lastName, email, administrator) values(1, 'John', 'DOE', 'jdoe@xebia.fr', FALSE);
insert into USER(id, firstName, lastName, email, administrator) values(2, 'Clement', 'LARDEUR', 'clardeur@xebia.fr', TRUE);
insert into USER(id, firstName, lastName, email, administrator) values(3, 'Yann', 'RENAUT', 'yrenaut@xebia.fr', TRUE);
=======
delete from SLOT
insert into SLOT (id, title, description, videoAccepted, scheduleDate, duration, author_id, slotType) values (1, 'JSF 2 en action', STRINGDECODE('Utilisation de JSF2 pour cr\u00E9er une web app, accompagn\u00E9e de PrettyFaces (Friendly Url, Navigation), OmniFaces (Utils) et PrimeFaces (Components)...\n\nUn socle de base sera fourni et il sera demand\u00E9 aux participants d''ajouter des fonctionnalit\u00E9s de mani\u00E8re progressive. Diff\u00E9rentes solutions seront propos\u00E9es concernant certaines probl\u00E9matiques.'), FALSE, DATE '2013-01-01', '2 h', NULL, 5);
>>>>>>> e3cb67cebd2ad6e5281192e2718f4252fcdc5cea
