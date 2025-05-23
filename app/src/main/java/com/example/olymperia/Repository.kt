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

        "LaRioja" to listOf(
            PortSegment(4969490L, "Moncalvillo", "LaRioja", 252),
            PortSegment(10141775L, "Cruz de la Demanda", "LaRioja", 216),
            PortSegment(24924725L, "Peña Hincada", "LaRioja", 173),
            PortSegment(7401599L, "Montenegro (Viniegras)", "LaRioja", 136),
            PortSegment(38078264L, "Valdezcaray", "LaRioja", 130),
            PortSegment(3876941L, "Clavijo Ermita", "LaRioja", 122),
            PortSegment(25935237L, "La Rasa (Almarza)", "LaRioja", 117),
            PortSegment(6918981L, "La Rasa (Muro)", "LaRioja", 92),
            PortSegment(24185199L, "Pazuengos", "LaRioja", 80),
            PortSegment(17375890L, "Bonicaparra", "LaRioja", 73),
            PortSegment(38318105L, "Rivas de Tereso (San Vicente)", "LaRioja", 62),
            PortSegment(20030737L, "Rivas de Tereso (Labastida)", "LaRioja", 60),
            PortSegment(2677590L, "Monasterio Valvanera", "LaRioja", 51),
            PortSegment(4620338L, "Pradilla (La Rioja)", "LaRioja", 55),
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
