

drop database if exists fz2017;
create database fz2017 character set utf8 collate utf8_general_ci;

use fz2017;

#https://dev.mysql.com/doc/refman/5.7/en/datetime.html

create  table podatak(
sifra int not null primary key auto_increment,
opis varchar(255) not null,
samodatum date, #The DATE type is used for values with a date part but no time part. MySQL retrieves and displays DATE values in 'YYYY-MM-DD' format. The supported range is '1000-01-01' to '9999-12-31'.
vrijeme time, #MySQL retrieves and displays TIME values in 'HH:MM:SS' format (or 'HHH:MM:SS' format for large hours values). TIME values may range from '-838:59:59' to '838:59:59'
datumvrijeme datetime, #The DATETIME type is used for values that contain both date and time parts. MySQL retrieves and displays DATETIME values in 'YYYY-MM-DD HH:MM:SS' format. The supported range is '1000-01-01 00:00:00' to '9999-12-31 23:59:59'.
datumvrijemetimezone timestamp #MySQL converts TIMESTAMP values from the current time zone to UTC for storage, and back from UTC to the current time zone for retrieval.
)engine=innodb CHARACTER SET utf8 COLLATE utf8_general_ci;


#na ofset vremenske zone utječe ljetno računanje vremena



#primjeri
SET time_zone='Europe/London';
SELECT datumvrijeme, UNIX_TIMESTAMP(datumvrijemetimezone), datumvrijemetimezone FROM podatak;

SET time_zone='Europe/Zagreb';
SELECT datumvrijeme, UNIX_TIMESTAMP(datumvrijemetimezone), datumvrijemetimezone FROM podatak;

#koliko ste dana stari
# select datediff("1980-12-07",now());

#Dosadašnji broj otkucaja Vašeg srca
select TIMESTAMPDIFF(MINUTE, '1980-12-07', now())*80; # ako je 80 prosječan broj

#pojedini djelovi datuma i vremena
select year(now()), month(now()), day(now()), hour(now()), minute(now()), second(now());
