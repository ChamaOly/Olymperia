package com.example.olymperia.repository

import com.example.olymperia.model.PortSegment

object PortRepository {

    // Puertos simulados manualmente organizados por provincias
    private val portsByProvince: Map<String, List<PortSegment>> = mapOf(
        "Burgos" to listOf(
            PortSegment(32223545L, "Picon Blanco (Arreondo)", "Burgos", 285),
            PortSegment(5356599L,  "Lagunas de Neila (Villavelayo)", "Burgos", 246),
            PortSegment(37003167L, "Picon Blanco", "Burgos", 233),
            PortSegment(8077840L,  "Lagunas de Neila (Fuensanza)", "Burgos", 226),
            PortSegment(24472088L, "Lagunas de Neila (Quintanar)", "Burgos", 179),
            PortSegment(16357364L, "Lagunas de Neila (Huerta)", "Burgos", 171),
            PortSegment(12192766L, "Orduña", "Burgos", 143),
            PortSegment(2674924L, "Altotero", "Burgos", 83),
            PortSegment(3702098L,  "La Sía (Burgos)", "Burgos", 81),
            PortSegment(34610875L, "Lunada (Sur)", "Burgos", 74),
            PortSegment(7235343L, "La EME", "Burgos", 67),
            PortSegment(38078715L, "Valle del Sol", "Burgos", 60),
            PortSegment(7471429L, "La Lora", "Burgos", 58),
            PortSegment(19004166L, "Estacas de Trueba (Sur)", "Burgos", 46),
            PortSegment(7185323L, "Manquillo", "Burgos", 42),
        ),
        "Soria" to listOf(
            PortSegment(25004224L, "Laguna Negra", "Soria", 110),
            PortSegment(18151410L, "Santa Ines (Vinuesa)", "Soria", 102),
            PortSegment(32498349L, "Santa Ines (Montenegro)", "Soria", 96),
            PortSegment(3806231L,  "Montenegro (Soria)", "Soria", 77),
            PortSegment(18226018L, "El Becedo", "Soria", 68),
            PortSegment(3247418L,  "Castroviejo", "Soria", 59),
            PortSegment(14632573L, "Cabeza Alta (Duruelo)", "Soria", 52)
        ),

        "Palencia" to listOf(
            PortSegment(18234286L, "Golobar(Espinilla)", "Palencia", 164),
            PortSegment(29084183L, "Golobar(Barruelo)", "Palencia", 148),
            PortSegment(2229339L, "Santuario Brezo", "Palencia", 62),
            PortSegment(25937945L, "La Varga(Ventanilla)", "Palencia", 55),
        ),

        "Avila" to listOf(
            PortSegment(2538911L, "Mijares(Mijares)", "Avila", 171),
            PortSegment(3065423L, "Peñanegra(Piedrahita)", "Avila", 149),
            PortSegment(7038877L, "La Centenera(Este)", "Avila", 143),
            PortSegment(7588297L, "Nogal del Barranco(San Pedro)", "Avila", 131),
            PortSegment(20758288L, "El Mediano(Herradon)", "Avila", 125),
            PortSegment(5828883L,"Mijares(Villanueva)", "Avila",110),
            PortSegment(2472188L, "Navalmoral", "Avila", 100),
            PortSegment(23285587L, "El Boquerón", "Avila", 61),
            PortSegment(20241383L, "Las Erillas(Pimpollar)", "Avila", 55),
        ),

        "Leon" to listOf(
            PortSegment(12504712L, "El Morredero(Ponferrada)", "Leon", 265),
            PortSegment(7030536L, "Llano de la Ovejas(Nogar)", "Leon", 275),
            PortSegment(37719614L, "Ancares(Tejedo)", "Leon", 255),
            PortSegment(36881021L, "Camperona", "Leon", 251),
            PortSegment(18531027L, "El Peñon(Truchas)", "Leon", 201),
            PortSegment(15439761L, "Campo de las Danzas", "Leon", 199),
            PortSegment(7182731L, "Cruz Ferro(Somoza)", "Leon", 197),
            PortSegment(12211025L, "O Cebreiro(Valcarce)", "Leon", 196),
            PortSegment(18505451L, "Panderrueda(Ponton)", "Leon", 175),
            PortSegment(14611219L, "Andarraso(Riello)", "Leon", 152),
            PortSegment(6974286L, "Peñalba de Santiago(Ponferrada)", "Leon", 144),
            PortSegment(19961802L, "Cueto Rosales(Riello)", "Leon", 126),
            PortSegment(3943829L, "Valdorria(Valdelapiego)", "Leon", 126),
            PortSegment(19961802L, "Cueto Rosales(Riello)", "Leon", 134),
            PortSegment(14610117L, "Vegarada-Rio Pinos(Redipuertas)", "Leon", 80),
            PortSegment(4984321L, "Valporquero(Carmenes", "Leon", 69),
            PortSegment(4214420L, "Aralla(Geras)", "Leon", 78),
            PortSegment(15441425L, "Aralla(Sena de Luna)", "Leon", 68),

        ),

        "Segovia" to listOf(
            PortSegment(37360567L, "Navapelegrín", "Segovia", 154),
            PortSegment(14929003L, "Navacerrada(Segovia)", "Segovia", 136),
            PortSegment(6690994L, "Navafría(Segovia)", "Segovia", 95),
            PortSegment(2911231L, "Fuente de la Reina", "Segovia", 94),
            PortSegment(16068106L, "La Quesera(Riaza)", "Segovia", 89),
        ),

        "Salamanca" to listOf(
            PortSegment(18742508L, "La Cocvatilla", "Salamanca", 240),
            PortSegment(25224261L, "El Travieso", "Salamanca", 210),
            PortSegment(19166914L, "las Batuecas", "Salamanca", 153),
            PortSegment(2136922L, "Peña de Francia", "Salamanca", 131),
            PortSegment(8236285L, "Saucelle", "Salamanca", 97),
        ),

        "Zamora" to listOf(
            PortSegment(21539294L, "El Peñón(Escuredo)", "Zamora", 197),
            PortSegment(2575439L, "Padornelo(Resquejo)", "Zamora", 130),
            PortSegment(3458555L, "Laguna de los Peces(Sanabria)", "Zamora", 101),
            PortSegment(7756538L, "Fermoselle", "Zamora", 44),
        ),

        "La Rioja" to listOf(
            PortSegment(4969490L, "Moncalvillo", "La Rioja", 252),
            PortSegment(10141775L, "Cruz de la Demanda", "La Rioja", 216),
            PortSegment(24924725L, "Peña Hincada", "La Rioja", 173),
            PortSegment(7401599L, "Montenegro (Viniegras)", "La Rioja", 136),
            PortSegment(38078264L, "Valdezcaray", "La Rioja", 130),
            PortSegment(3876941L, "Clavijo Ermita", "La Rioja", 122),
            PortSegment(25935237L, "La Rasa (Almarza)", "La Rioja", 117),
            PortSegment(6918981L, "La Rasa (Muro)", "La Rioja", 92),
            PortSegment(24185199L, "Pazuengos", "La Rioja", 80),
            PortSegment(17375890L, "Bonicaparra", "La Rioja", 73),
            PortSegment(38318105L, "Rivas de Tereso (San Vicente)", "La Rioja", 62),
            PortSegment(20030737L, "Rivas de Tereso (Labastida)", "La Rioja", 60),
            PortSegment(2677590L, "Monasterio Valvanera", "La Rioja", 51),
            PortSegment(4620338L, "Pradilla (La Rioja)", "La Rioja", 55),
        ),
        "Cantabria" to listOf(
            PortSegment(32392662L, "Salto de la Cabra(Hermida)", "Cantabria", 346),
            PortSegment(17475314L, "San Glorio(Potes)", "Cantabria", 243),
            PortSegment(14694961L, "Lunada(Cantabria)", "Cantabria", 226),
            PortSegment(5455172L, "Machucos(Arreondo)", "Cantabria", 226),
            PortSegment(3640462L, "Machucos(La Pedrosa)", "Cantabria", 215),
            PortSegment(4953680L, "La Sia(Cantabria)", "Cantabria", 212),
            PortSegment(15198665L, "La Estranguada", "Cantabria", 205),
            PortSegment(29851818L, "Fuente del Chivo", "Cantabria", 199),
            PortSegment(4216743L, "Piedrasluengas(Cantabria)", "Cantabria", 191),
            PortSegment(32474414L, "Pico Jano", "Cantabria", 187),
            PortSegment(12804943L, "Peña Cabarga", "Cantabria", 186),
            PortSegment(8757333L, "El Escudo(Luena)", "Cantabria", 185),
            PortSegment(13353377L, "Piedrasluengas(Potes)", "Cantabria", 155),
            PortSegment(1217983L, "Collada de Brenes", "Cantabria", 153),
            PortSegment(25023408L, "Subida a las Nieves", "Cantabria", 153),
            PortSegment(21474249L, "Los Tornos(Lanestrosa)", "Cantabria", 151),
            PortSegment(9593661L, "Palombera(Cantabria)", "Cantabria", 147),
            PortSegment(2357211L, "Fuente De", "Cantabria", 136),
            PortSegment(25432109L, "Estacas de Trueba(Vega)", "Cantabria", 135),
            PortSegment(10062370L, "Los Tornos(Fresnedo)", "Cantabria", 134),
            PortSegment(21512454L, "La Rasía(Vega)", "Cantabria", 131),
            PortSegment(20388731L, "El Caracol(San Roque)", "Cantabria", 130),
            PortSegment(6904358L, "La Estranguada(Lloreda)", "Cantabria", 120),
            PortSegment(4867854L, "El Caracol(Selaya)", "Cantabria", 117),
            PortSegment(2868593L, "Alisas(Cavada)", "Cantabria", 110),
            PortSegment(4637627L, "Alisas(Arreondo)", "Cantabria", 108),
            PortSegment(7091574L, "La Lora(Rocamundo)", "Cantabria", 106),
            PortSegment(17030044L, "Ason", "Cantabria", 98),
            PortSegment(15513178L, "Matanela(Vega)", "Cantabria", 96),
            PortSegment(4813356L, "Braguía(Selaya)", "Cantabria",81),
            PortSegment(6706542L, "Campo la Cruz", "Cantabria", 69),
            PortSegment(1032653L, "Braguía(Vega)", "Cantabria", 61),
        ),

        "Asturias" to listOf(
            PortSegment(32707585L, "Angliru(Morcín)", "Asturias", 528),
            PortSegment(5399200L, "Angliru(Riosa)", "Asturias", 515),
            PortSegment(8304058L, "Gamoniteiro(Cobertoria)", "Asturias", 492),
            PortSegment(9236245L, "Gamoniteiro(Cordal)", "Asturias", 452),
            PortSegment(37002050L, "Cuitu Negru", "Asturias", 394),
            PortSegment(10441498L, "Ermita del Alba", "Asturias", 294),
            PortSegment(10332108L, "Jitu de Escarandi", "Asturias", 289),
            PortSegment(17389554L, "La Cobertoria(Pola)", "Asturias", 278),
            PortSegment(7041022L, "San Lorenzo(Riera)", "Asturias", 271),
            PortSegment(12157376L, "Lagos de Covadonga", "Asturias", 267),
            PortSegment(29974459L, "San Lorenzo(Teverga)", "Asturias", 265),
            PortSegment(11610117L, "Amieva", "Asturias", 245),
            PortSegment(25745566L, "La Farrapona", "Asturias", 243),
            PortSegment(21324992L, "Les Praeres", "Asturias", 240),
            PortSegment(26626560L, "Fancuaya", "Asturias", 232),
            PortSegment(4054360L, "Ventana(Teverga)", "Asturias", 224),
            PortSegment(20890303L, "Marabio(San Pedro)", "Asturias", 217),
            PortSegment(32800199L, "Collada Llomena", "Asturias", 212),
            PortSegment(28692264L, "Coto Bello", "Asturias", 212),
            PortSegment(14505188L, "Santuario del Acebo(Narcea)", "Asturias", 212),
            PortSegment(7429623L, "Casielles", "Asturias", 209),
            PortSegment(11812964L, "Cruz de Linares(Proaza)", "Asturias", 196),
            PortSegment(29478301L, "San Isidro(Felechosa)", "Asturias", 194),
            PortSegment(6734940L, "La Cobertoria(Santa Marina)", "Asturias", 191),
            PortSegment(25678891L, "Peñas del Viento(Argaton)", "Asturias", 183),
            PortSegment(22062102L, "Somiedo(Pola)", "Asturias", 178),
            PortSegment(1537722L, "Bermiego(Quiros)", "Asturias", 174),
            PortSegment(30038195L, "Leitariegos(Bimeda)", "Asturias", 169),
            PortSegment(760782L, "El Cordal(Pola)", "Asturias", 151),
            PortSegment(1798409L, "El Fito", "Asturias", 150),
            PortSegment(7541574L, "Valle de Lago(Somiedo)", "Asturias", 149),
            PortSegment(32495652L, "La Colladiella(Sotrondio)", "Asturias", 145),
            PortSegment(4197060L, "Collada Llomena(Beleño)", "Asturias", 140),
            PortSegment(25518816L, "La Cobertoria(Cortes)", "Asturias", 131),
            PortSegment(14543886L, "El Fito(Arriondas)", "Asturias",104),
            PortSegment(6706482L, "Leitariegos(Villablino)", "Asturias", 91),
            PortSegment(24491199L, "Naranco", "Asturias", 83),
            ),

        "Alava" to listOf(
            PortSegment(8039863L, "Pagolar(Llodio)", "Alava", 272),
            PortSegment(10706734L, "Txibiarte(Amurrio)", "Alava", 133),
            PortSegment(20701352L, "Herrera(Sur)", "Alava", 130),
            PortSegment(6297710L, "Garrastatxu(Barandio)", "Alava", 117),
            PortSegment(5238031L, "Krutzeta", "Alava", 101),
            PortSegment(25843696L, "Ozeka(Erbi)", "Alava", 89),
            PortSegment(8022331L, "Opakua", "Alava", 82),
            PortSegment(17941136L, "Herrera(Peñacerrada)", "Alava", 72),

            ),

        "Bizkaia" to listOf(


            ),
        "Guipuzkoa" to listOf(
            PortSegment(11765408L, "Arrate(Mugitzazar)", "Guipuzkoa", 197),
            PortSegment(32367358L, "Usurbe(Beasain)", "Guipuzkoa", 197),
            PortSegment(4342664L, "Azurki(Zestoa)", "Guipuzkoa", 171),
            PortSegment(8069131L, "Arrate(Mandiola)", "Guipuzkoa", 170),
            PortSegment(10827651L, "Karakate(Ezoia)", "Guipuzkoa", 168),
            PortSegment(36347539L, "Azurki(Lastur)", "Guipuzkoa", 167),
            PortSegment(34765090L, "Karakate(Placencia)", "Guipuzkoa", 157),
            PortSegment(20170841L, "Castillo del Ingles(Oiartzun)", "Guipuzkoa", 154),
            PortSegment(24543011L, "Etumeta(Artzalluz)", "Guipuzkoa", 143),
            PortSegment(9633114L, "Azurki(Azkoitia)", "Guipuzkoa", 143),
            PortSegment(11017894L, "Azurki(Elgoibar)", "Guipuzkoa", 135),
            PortSegment(7210668L, "Urrati(Azpeitia)", "Guipuzkoa", 125),
            PortSegment(24357151L, "Gorla(Bergara)", "Guipuzkoa", 121),
            PortSegment(10703636L, "Jaizkibel(Lezo)", "Guipuzkoa", 108),
            PortSegment(20609355L, "Bianditz(Oiartzun)", "Guipuzkoa", 108),
            PortSegment(37575809L, "Otzaurte(Zegama)", "Guipuzkoa", 108),
            PortSegment(3662570L, "Arantzazu(Oñate)", "Guipuzkoa", 103),
            PortSegment(32807399L, "Gorla(Azpeitia)", "Guipuzkoa", 102),
            PortSegment(16988396L, "Andatzarrate(Asteasu)", "Guipuzkoa", 99),
            PortSegment(674906L, "Urkizu(Tolosa)", "Guipuzkoa", 96),
            PortSegment(32430384L, "Karabieta(Elorrio)", "Guipuzkoa", 93),
            PortSegment(5614314L, "Ixua(Etxebarria)", "Guipuzkoa", 86),
            PortSegment(29183125L, "Gatzaga(Eskoriatza)", "Guipuzkoa", 85),
            PortSegment(34700807L, "Jaizkibel(Ondarribia)", "Guipuzkoa", 81),
            PortSegment(27976791L, "Mandubia(Nuarbe)", "Guipuzkoa", 67),
            PortSegment(17135870L, "Mandubia(Zubitik)", "Guipuzkoa", 55),


        ),
        "Pirineos" to listOf(

        ),
        "A Coruña" to listOf(
            PortSegment(4819355L, "Tremuzo(Outes)", "A Coruña", 150),
            PortSegment(1725608L, "Forcados(Caramiñal)", "A Coruña", 139),
            PortSegment(28662595L, "Iroite(Runs)", "A Coruña", 114),
            PortSegment(4550520L, "Enxa(Porto)", "A Coruña", 112),
            PortSegment(19227305L, "Paxareiras(Carnota)", "A Coruña", 110),
            PortSegment(14522655L, "Paxareiras(Muros)", "A Coruña", 72),
            PortSegment(7061643L, "Paxareiras(Suevos)", "A Coruña", 41),

        ),
        "Lugo" to listOf(
            PortSegment(35317791L, "Ancares(Murias)", "Lugo", 360),
            PortSegment(15016137L, "Ancares(Balouta)", "Lugo", 197),
            PortSegment(12153013L, "O Cebreiro(Valcarce)", "Lugo", 162),
            PortSegment(2289630L, "Sierra Morela(Navia)", "Lugo", 123),
            PortSegment(9453385L, "O Cebreiro(Piedrafita)", "Lugo", 100),
        ),

        "Ourense" to listOf(
            PortSegment(29849021L, "Fonte da Cova(Sobradelo)", "Ourense", 297),
            PortSegment(7968882L, "Cabeza de Manzaneda", "Ourense", 245),

        ),
        "Pontevedra" to listOf(
            PortSegment(7958452L, "San Fins(Cabeiras)", "Pontevedra", 167),
            PortSegment(1884515L, "Xiabre(Catoira)", "Pontevedra", 157),
            PortSegment(4999301L, "Xiabre(Villagarcia)", "Pontevedra", 143),
            PortSegment(1211083L, "Monte Aloia(Tuy)", "Pontevedra", 136),


            ),
        "Navarra" to listOf(
            PortSegment(15768597L, "Ibañeta(Valcarlos)", "Navarra", 231),
            PortSegment(7198209L, "Higa Monreal(Monreal)", "Navarra", 248),
            PortSegment(7851604L, "San Miguel de Aralar(Baraibar)", "Navarra", 221),
            PortSegment(15513010L, "Otxondo(Urdax)", "Navarra", 210),
            PortSegment(29305909L, "Piedra de San Martin(Isaba)", "Navarra", 160),
            PortSegment(1367226L, "Aritxulegi(Oyartzun)", "Navarra", 147),
            PortSegment(15014692L, "Bagordi(Baztan)", "Navarra", 140),
            PortSegment(18558948L, "Artesiaga(Irurita)", "Navarra", 139),
            PortSegment(27734528L, "Larrau(Otxagabia)", "Navarra", 132),
            PortSegment(19253544L, "Urrizketa(Baztan)", "Navarra", 121),
            PortSegment(35392298L, "Belate(Baztan)", "Navarra", 120),
            PortSegment(9190619L, "Tapla(Otxagabia)", "Navarra", 105),
            PortSegment(24640399L, "Aspirotz(Betelu)", "Navarra", 99),
            PortSegment(8626001L, "Agiña(Oiartzun)", "Navarra", 90),
            PortSegment(9794751L, "Matamachos(Garde)", "Navarra", 83),
            PortSegment(38165843L, "Etxauri(Etxauri)", "Navarra", 81),
            PortSegment(5968993L, "Lizarraga(Lizarraga)", "Navarra", 79),
            PortSegment(3838853L, "Lizarraga(Urbasa)", "Navarra", 73),
            PortSegment(31320928, "Goñi(Arteta)", "Navarra", 64),
            PortSegment(643499L, "Zuarrarrate(Irurtzub)", "Navarra", 54),



            ),
        "Huesca" to listOf(

        ),
        "Teruel" to listOf(

        ),
        "Zaragoza" to listOf(

        ),
        "Barcelona" to listOf(

        ),
        "Girona" to listOf(

        ),
        "Lleida" to listOf(

        ),
        "Tarragona" to listOf(

        ),
        "Albacete" to listOf(
            PortSegment(37632723L, "Monte Ardal(Yeste)", "Albacete", 175),
            PortSegment(1505178L, "La Borriqueta(Yeste)", "Albacete", 174),
            PortSegment(7653297L, "El Barrancazo(Vianos)", "Albacete", 111),
            PortSegment(30673042L, "Quebradas(Raia)", "Albacete", 110),
            PortSegment(5370494L, "Las Crucetillas(Riopar)", "Albcete", 89),


            ),
        "Ciudad Real" to listOf(
            PortSegment(9405947L, "Los Rehoyos(Solana)", "Ciudad Real", 78),

        ),
        "Cuenca" to listOf(
            PortSegment(6612565L, "Belvalle(Martinete)", "Cuenca", 103),
            PortSegment(24826880L, "Cabeza del Hoyo(Villar)", "Cuenca", 89),
            PortSegment(12680249L, "Cubillo(Huélamo)", "Cuenca", 76),
            PortSegment(7393158L, "Las Majadas(Villalba)", "Cuenca", 72),
            PortSegment(6758434L, "Ciudad Encantada(Villalba)", "Cuenca", 69),

            ),

        "Guadalajara" to listOf(
            PortSegment(10701848L, "Alto Rey(Bustares)", "Guadalajara", 161),
            PortSegment(17576757L, "La Quesera", "Guadalajara", 160),
            PortSegment(32742816L, "Peñalba de la Sierra(El Cardoso)", "Guadalajara", 101),

            ),

        "Toledo" to listOf(
            PortSegment(32242661L, "El Pielago(San Vicente)", "Toledo", 150),
            PortSegment(18162410L, "El Pielago(Navamorcuende)", "Toledo", 95),
            PortSegment(28053901L, "El Robledillo(San Pablo)", "Toledo", 84),
            PortSegment(7064555L, "Risco de las Paradas(Navahermosa)", "Toledo", 67),
            ),

        "Madrid" to listOf(
            PortSegment(10576170L, "La Bola del Mundo", "Madrid", 352),
            PortSegment(13081204L, "Navacerrada(Madrid)", "Madrid", 188),
            PortSegment(9998668L, "Morcuera(Miraflores)", "Madrid", 141),
            PortSegment(4326384L, "Abantos(El Escorial)", "Madrid", 134),
            PortSegment(20902593L, "Navafría(Lozoya)", "Madrid", 117),
            PortSegment(20232812L, "Alto del Leon(Guadarrama)", "Madrid", 116),
            PortSegment(28681327L, "Cotos(Rascafria)", "Madrid", 112),
            PortSegment(15326527L, "Morcuera(Rascafría)", "Madrid", 110),
            PortSegment(24091288L, "La Puebla(Pradena)", "Madrid", 106),
            PortSegment(15250516L, "La Puebla(Puebla)", "Madrid", 93),
            PortSegment(801742L, "Canencia(Bustarviejo)", "Madrid", 84),
            PortSegment(1272202L, "Canencia(Canencia)", "Madrid", 78),
            PortSegment(33313500L, "El Atazar(Patones)", "Madrid", 71),
            PortSegment(9468103L, "Cruz Verde(Escorial)", "Madrid", 40),
            PortSegment(2135660L, "Cruz Verde(Robledo)", "Madrid", 40),
            PortSegment(14628218L, "Cruz Verde(Zarzalejo)", "Madrid", 40),


            ),
        "SC Tenerife" to listOf(

        ),
        "Las Palmas" to listOf(

        ),
        "Almería" to listOf(

        ),
        "Cádiz" to listOf(

        ),
        "Córdoba" to listOf(

        ),
        "Granada" to listOf(

        ),
        "Huelva" to listOf(

        ),
        "Jaén" to listOf(

        ),
        "Málaga" to listOf(

        ),
        "Sevilla" to listOf(

        ),
        "Badajoz" to listOf(

        ),
        "Cáceres" to listOf(

        ),
        "Baleares" to listOf(

        ),
        "Valencia" to listOf(

        ),
        "Alicante" to listOf(

        ),
        "Castellón" to listOf(

        ),
        "Murcia" to listOf(

        ),




        // Agrega más provincias y puertos aquí si lo necesitas
    )


    fun getAllSegments(): List<PortSegment> {
        return portsByProvince.values.flatten()
    }

    fun getSegmentsByProvince(province: String): List<PortSegment> {
        return portsByProvince[province] ?: emptyList()
    }

    fun getAllProvinces(): List<String> {
        return portsByProvince.keys.toList()
    }

    fun getSegmentById(id: Long): PortSegment? {
        return portsByProvince.values.flatten().find { it.id == id }
    }
}
