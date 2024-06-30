package com.example.prueba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryData {
    private final Map<String, String> phoneCodeToCountryMap;
    private final List<String> countryNames;

    public CountryData() {
        phoneCodeToCountryMap = new HashMap<>();
        countryNames = new ArrayList<>();
        initializeData();
    }   private void initializeData() {

        phoneCodeToCountryMap.put("+681", "Wallis and Futuna");
        phoneCodeToCountryMap.put("+354", "Iceland");
        phoneCodeToCountryMap.put("+352", "Luxembourg");
        phoneCodeToCountryMap.put("+223", "Mali");
        phoneCodeToCountryMap.put("+269", "Comoros");
        phoneCodeToCountryMap.put("+61", "Australia");
        phoneCodeToCountryMap.put("+372", "Estonia");
        phoneCodeToCountryMap.put("+1", "Canada");
        phoneCodeToCountryMap.put("+375", "Belarus");
        phoneCodeToCountryMap.put("+592", "Guyana");
        phoneCodeToCountryMap.put("+220", "Gambia");
        phoneCodeToCountryMap.put("+216", "Tunisia");
        phoneCodeToCountryMap.put("+237", "Cameroon");
        phoneCodeToCountryMap.put("+250", "Rwanda");
        phoneCodeToCountryMap.put("+855", "Cambodia");
        phoneCodeToCountryMap.put("+229", "Benin");
        phoneCodeToCountryMap.put("+30", "Greece");
        phoneCodeToCountryMap.put("+82", "South Korea");
        phoneCodeToCountryMap.put("+230", "Mauritius");
        phoneCodeToCountryMap.put("+1340", "United States Virgin Islands");
        phoneCodeToCountryMap.put("+35818", "Aland Islands");
        phoneCodeToCountryMap.put("+378", "San Marino");
        phoneCodeToCountryMap.put("+960", "Maldives");
        phoneCodeToCountryMap.put("+678", "Vanuatu");
        phoneCodeToCountryMap.put("+265", "Malawi");
        phoneCodeToCountryMap.put("+20", "Egypt");
        phoneCodeToCountryMap.put("+221", "Senegal");
        phoneCodeToCountryMap.put("+995", "Georgia");
        phoneCodeToCountryMap.put("+64", "New Zealand");
        phoneCodeToCountryMap.put("+238", "Cape Verde");
        phoneCodeToCountryMap.put("+39", "Italy");
        phoneCodeToCountryMap.put("+377", "Monaco");
        phoneCodeToCountryMap.put("+421", "Slovakia");
        phoneCodeToCountryMap.put("+598", "Uruguay");
        phoneCodeToCountryMap.put("+856", "Laos");
        phoneCodeToCountryMap.put("+298", "Faroe Islands");
        phoneCodeToCountryMap.put("+683", "Niue");
        phoneCodeToCountryMap.put("+389", "North Macedonia");
        phoneCodeToCountryMap.put("+56", "Chile");
        phoneCodeToCountryMap.put("+357", "Cyprus");
        phoneCodeToCountryMap.put("+853", "Macau");
        phoneCodeToCountryMap.put("+503", "El Salvador");
        phoneCodeToCountryMap.put("+962", "Jordan");
        phoneCodeToCountryMap.put("+1876", "Jamaica");
        phoneCodeToCountryMap.put("+1246", "Barbados");
        phoneCodeToCountryMap.put("+2125288", "Western Sahara");
        phoneCodeToCountryMap.put("+2125289", "Western Sahara");
        phoneCodeToCountryMap.put("+974", "Qatar");
        phoneCodeToCountryMap.put("+502", "Guatemala");
        phoneCodeToCountryMap.put("+691", "Micronesia");
        phoneCodeToCountryMap.put("+1664", "Montserrat");
        phoneCodeToCountryMap.put("+675", "Papua New Guinea");
        phoneCodeToCountryMap.put("+55", "Brazil");
        phoneCodeToCountryMap.put("+370", "Lithuania");
        phoneCodeToCountryMap.put("+594", "French Guiana");
        phoneCodeToCountryMap.put("+373", "Moldova");
        phoneCodeToCountryMap.put("+996", "Kyrgyzstan");
        phoneCodeToCountryMap.put("+599", "Curacao");
        phoneCodeToCountryMap.put("+84", "Vietnam");
        phoneCodeToCountryMap.put("+244", "Angola");
        phoneCodeToCountryMap.put("+60", "Malaysia");
        phoneCodeToCountryMap.put("+41", "Switzerland");
        phoneCodeToCountryMap.put("+66", "Thailand");
        phoneCodeToCountryMap.put("+1809", "Dominican Republic");
        phoneCodeToCountryMap.put("+1829", "Dominican Republic");
        phoneCodeToCountryMap.put("+1849", "Dominican Republic");
        phoneCodeToCountryMap.put("+998", "Uzbekistan");
        phoneCodeToCountryMap.put("+235", "Chad");
        phoneCodeToCountryMap.put("+224", "Guinea");
        phoneCodeToCountryMap.put("+1649", "Turks and Caicos Islands");
        phoneCodeToCountryMap.put("+1787", "Puerto Rico");
        phoneCodeToCountryMap.put("+1939", "Puerto Rico");
        phoneCodeToCountryMap.put("+975", "Bhutan");
        phoneCodeToCountryMap.put("+1345", "Cayman Islands");
        phoneCodeToCountryMap.put("+692", "Marshall Islands");
        phoneCodeToCountryMap.put("+1264", "Anguilla");
        phoneCodeToCountryMap.put("+222", "Mauritania");
        phoneCodeToCountryMap.put("+596", "Martinique");
        phoneCodeToCountryMap.put("+972", "Israel");
        phoneCodeToCountryMap.put("+593", "Ecuador");
        phoneCodeToCountryMap.put("+1473", "Grenada");
        phoneCodeToCountryMap.put("+385", "Croatia");
        phoneCodeToCountryMap.put("+673", "Brunei");
        phoneCodeToCountryMap.put("+964", "Iraq");
        phoneCodeToCountryMap.put("+81", "Japan");
        phoneCodeToCountryMap.put("+266", "Lesotho");
        phoneCodeToCountryMap.put("+688", "Tuvalu");
        phoneCodeToCountryMap.put("+358", "Finland");
        phoneCodeToCountryMap.put("+211", "South Sudan");
        phoneCodeToCountryMap.put("+268", "United States Minor Outlying Islands");
        phoneCodeToCountryMap.put("+290", "Saint Helena");
        phoneCodeToCountryMap.put("+247", "Saint Helena");
        phoneCodeToCountryMap.put("+93", "Afghanistan");
        phoneCodeToCountryMap.put("+677", "Solomon Islands");
        phoneCodeToCountryMap.put("+86", "China");
        phoneCodeToCountryMap.put("+291", "Eritrea");
        phoneCodeToCountryMap.put("+73", "Russia");
        phoneCodeToCountryMap.put("+74", "Russia");
        phoneCodeToCountryMap.put("+75", "Russia");
        phoneCodeToCountryMap.put("+78", "Russia");
        phoneCodeToCountryMap.put("+79", "Russia");
        phoneCodeToCountryMap.put("+376", "Andorra");
        phoneCodeToCountryMap.put("+374", "Armenia");
        phoneCodeToCountryMap.put("+43", "Austria");
        phoneCodeToCountryMap.put("+597", "Suriname");
        phoneCodeToCountryMap.put("+34", "Spain");
        phoneCodeToCountryMap.put("+690", "Tokelau");
        phoneCodeToCountryMap.put("+1242", "Bahamas");
        phoneCodeToCountryMap.put("+1721", "Sint Maarten");
        phoneCodeToCountryMap.put("+501", "Belize");
        phoneCodeToCountryMap.put("+46", "Sweden");
        phoneCodeToCountryMap.put("+267", "Botswana");
        phoneCodeToCountryMap.put("+971", "United Arab Emirates");
        phoneCodeToCountryMap.put("+98", "Iran");
        phoneCodeToCountryMap.put("+241", "Gabon");
        phoneCodeToCountryMap.put("+1869", "Saint Kitts and Nevis");
        phoneCodeToCountryMap.put("+240", "Equatorial Guinea");
        phoneCodeToCountryMap.put("+239", "Sao Tomé and Príncipe");
        phoneCodeToCountryMap.put("+299", "Greenland");
        phoneCodeToCountryMap.put("+880", "Bangladesh");
        phoneCodeToCountryMap.put("+40", "Romania");
        phoneCodeToCountryMap.put("+246", "British Indian Ocean Territory");
        phoneCodeToCountryMap.put("+249", "Sudan");
        phoneCodeToCountryMap.put("+387", "Bosnia and Herzegovina");
        phoneCodeToCountryMap.put("+356", "Malta");
        phoneCodeToCountryMap.put("+248", "Seychelles");
        phoneCodeToCountryMap.put("+94", "Sri Lanka");
        phoneCodeToCountryMap.put("+52", "Mexico");
        phoneCodeToCountryMap.put("+967", "Yemen");
        phoneCodeToCountryMap.put("+1284", "British Virgin Islands");
        phoneCodeToCountryMap.put("+31", "Netherlands");
        phoneCodeToCountryMap.put("+595", "Paraguay");
        phoneCodeToCountryMap.put("+44", "United Kingdom");
        phoneCodeToCountryMap.put("+58", "Venezuela");
        phoneCodeToCountryMap.put("+47", "Bouvet Island");
        phoneCodeToCountryMap.put("+380", "Ukraine");
        phoneCodeToCountryMap.put("+212", "Morocco");
        phoneCodeToCountryMap.put("+351", "Portugal");
        phoneCodeToCountryMap.put("+92", "Pakistan");
        phoneCodeToCountryMap.put("+1784", "Saint Vincent and the Grenadines");
        phoneCodeToCountryMap.put("+850", "North Korea");
        phoneCodeToCountryMap.put("+386", "Slovenia");
        phoneCodeToCountryMap.put("+225", "Ivory Coast");
        phoneCodeToCountryMap.put("+970", "Palestine");
        phoneCodeToCountryMap.put("+32", "Belgium");
        phoneCodeToCountryMap.put("+263", "Zimbabwe");
        phoneCodeToCountryMap.put("+255", "Tanzania");
        phoneCodeToCountryMap.put("+228", "Togo");
        phoneCodeToCountryMap.put("+682", "Cook Islands");
        phoneCodeToCountryMap.put("+687", "New Caledonia");
        phoneCodeToCountryMap.put("+1758", "Saint Lucia");
        phoneCodeToCountryMap.put("+48", "Poland");
        phoneCodeToCountryMap.put("+685", "Samoa");
        phoneCodeToCountryMap.put("+420", "Czechia");
        phoneCodeToCountryMap.put("+62", "Indonesia");
        phoneCodeToCountryMap.put("+591", "Bolivia");
        phoneCodeToCountryMap.put("+57", "Colombia");
        phoneCodeToCountryMap.put("+504", "Honduras");
        phoneCodeToCountryMap.put("+45", "Denmark");
        phoneCodeToCountryMap.put("+236", "Central African Republic");
        phoneCodeToCountryMap.put("+218", "Libya");
        phoneCodeToCountryMap.put("+245", "Guinea-Bissau");
        phoneCodeToCountryMap.put("+976", "Mongolia");
        phoneCodeToCountryMap.put("+243", "DR Congo");
        phoneCodeToCountryMap.put("+674", "Nauru");
        phoneCodeToCountryMap.put("+963", "Syria");
        phoneCodeToCountryMap.put("+382", "Montenegro");
        phoneCodeToCountryMap.put("+33", "France");
        phoneCodeToCountryMap.put("+260", "Zambia");
        phoneCodeToCountryMap.put("+95", "Myanmar");
        phoneCodeToCountryMap.put("+508", "Saint Pierre and Miquelon");
        phoneCodeToCountryMap.put("+506", "Costa Rica");
        phoneCodeToCountryMap.put("+252", "Somalia");
        phoneCodeToCountryMap.put("+353", "Ireland");
        phoneCodeToCountryMap.put("+689", "French Polynesia");
        phoneCodeToCountryMap.put("+36", "Hungary");
        phoneCodeToCountryMap.put("+90", "Turkey");
        phoneCodeToCountryMap.put("+968", "Oman");
        phoneCodeToCountryMap.put("+1671", "Guam");
        phoneCodeToCountryMap.put("+977", "Nepal");
        phoneCodeToCountryMap.put("+423", "Liechtenstein");
        phoneCodeToCountryMap.put("+261", "Madagascar");
        phoneCodeToCountryMap.put("+76", "Kazakhstan");
        phoneCodeToCountryMap.put("+77", "Kazakhstan");
        phoneCodeToCountryMap.put("+253", "Djibouti");
        phoneCodeToCountryMap.put("+350", "Gibraltar");
        phoneCodeToCountryMap.put("+264", "Namibia");
        phoneCodeToCountryMap.put("+242", "Republic of the Congo");
        phoneCodeToCountryMap.put("+49", "Germany");
        phoneCodeToCountryMap.put("+3906698", "Vatican City");
        phoneCodeToCountryMap.put("+379", "Vatican City");
        phoneCodeToCountryMap.put("+262", "French Southern and Antarctic Lands");
        phoneCodeToCountryMap.put("+355", "Albania");
        phoneCodeToCountryMap.put("+213", "Algeria");
        phoneCodeToCountryMap.put("+509", "Haiti");
        phoneCodeToCountryMap.put("+4779", "Svalbard and Jan Mayen");
        phoneCodeToCountryMap.put("+676", "Tonga");
        phoneCodeToCountryMap.put("+852", "Hong Kong");
        phoneCodeToCountryMap.put("+65", "Singapore");
        phoneCodeToCountryMap.put("+1268", "Antigua and Barbuda");
        phoneCodeToCountryMap.put("+966", "Saudi Arabia");
        phoneCodeToCountryMap.put("+53", "Cuba");
        phoneCodeToCountryMap.put("+507", "Panama");
        phoneCodeToCountryMap.put("+231", "Liberia");
        phoneCodeToCountryMap.put("+994", "Azerbaijan");
        phoneCodeToCountryMap.put("+371", "Latvia");
        phoneCodeToCountryMap.put("+686", "Kiribati");
        phoneCodeToCountryMap.put("+251", "Ethiopia");
        phoneCodeToCountryMap.put("+227", "Niger");
        phoneCodeToCountryMap.put("+1868", "Trinidad and Tobago");
        phoneCodeToCountryMap.put("+232", "Sierra Leone");
        phoneCodeToCountryMap.put("+258", "Mozambique");
        phoneCodeToCountryMap.put("+680", "Palau");
        phoneCodeToCountryMap.put("+679", "Fiji");
        phoneCodeToCountryMap.put("+672", "Norfolk Island");
        phoneCodeToCountryMap.put("+993", "Turkmenistan");
        phoneCodeToCountryMap.put("+992", "Tajikistan");
        phoneCodeToCountryMap.put("+961", "Lebanon");
        phoneCodeToCountryMap.put("+234", "Nigeria");
        phoneCodeToCountryMap.put("+1201", "United States");
        phoneCodeToCountryMap.put("+1202", "United States");
        phoneCodeToCountryMap.put("+1203", "United States");
        phoneCodeToCountryMap.put("+1205", "United States");
        phoneCodeToCountryMap.put("+1206", "United States");
        phoneCodeToCountryMap.put("+1207", "United States");
        phoneCodeToCountryMap.put("+1208", "United States");
        phoneCodeToCountryMap.put("+1209", "United States");
        phoneCodeToCountryMap.put("+1210", "United States");
        phoneCodeToCountryMap.put("+1212", "United States");
        phoneCodeToCountryMap.put("+1213", "United States");
        phoneCodeToCountryMap.put("+1214", "United States");
        phoneCodeToCountryMap.put("+1215", "United States");
        phoneCodeToCountryMap.put("+1216", "United States");
        phoneCodeToCountryMap.put("+1217", "United States");
        phoneCodeToCountryMap.put("+1218", "United States");
        phoneCodeToCountryMap.put("+1219", "United States");
        phoneCodeToCountryMap.put("+1220", "United States");
        phoneCodeToCountryMap.put("+1224", "United States");
        phoneCodeToCountryMap.put("+1225", "United States");
        phoneCodeToCountryMap.put("+1227", "United States");
        phoneCodeToCountryMap.put("+1228", "United States");
        phoneCodeToCountryMap.put("+1229", "United States");
        phoneCodeToCountryMap.put("+1231", "United States");
        phoneCodeToCountryMap.put("+1234", "United States");
        phoneCodeToCountryMap.put("+1239", "United States");
        phoneCodeToCountryMap.put("+1240", "United States");
        phoneCodeToCountryMap.put("+1248", "United States");
        phoneCodeToCountryMap.put("+1251", "United States");
        phoneCodeToCountryMap.put("+1252", "United States");
        phoneCodeToCountryMap.put("+1253", "United States");
        phoneCodeToCountryMap.put("+1254", "United States");
        phoneCodeToCountryMap.put("+1256", "United States");
        phoneCodeToCountryMap.put("+1260", "United States");
        phoneCodeToCountryMap.put("+1262", "United States");
        phoneCodeToCountryMap.put("+1267", "United States");
        phoneCodeToCountryMap.put("+1269", "United States");
        phoneCodeToCountryMap.put("+1270", "United States");
        phoneCodeToCountryMap.put("+1272", "United States");
        phoneCodeToCountryMap.put("+1274", "United States");
        phoneCodeToCountryMap.put("+1276", "United States");
        phoneCodeToCountryMap.put("+1281", "United States");
        phoneCodeToCountryMap.put("+1283", "United States");
        phoneCodeToCountryMap.put("+1301", "United States");
        phoneCodeToCountryMap.put("+1302", "United States");
        phoneCodeToCountryMap.put("+1303", "United States");
        phoneCodeToCountryMap.put("+1304", "United States");
        phoneCodeToCountryMap.put("+1305", "United States");
        phoneCodeToCountryMap.put("+1307", "United States");
        phoneCodeToCountryMap.put("+1308", "United States");
        phoneCodeToCountryMap.put("+1309", "United States");
        phoneCodeToCountryMap.put("+1310", "United States");
        phoneCodeToCountryMap.put("+1312", "United States");
        phoneCodeToCountryMap.put("+1313", "United States");
        phoneCodeToCountryMap.put("+1314", "United States");
        phoneCodeToCountryMap.put("+1315", "United States");
        phoneCodeToCountryMap.put("+1316", "United States");
        phoneCodeToCountryMap.put("+1317", "United States");
        phoneCodeToCountryMap.put("+1318", "United States");
        phoneCodeToCountryMap.put("+1319", "United States");
        phoneCodeToCountryMap.put("+1320", "United States");
        phoneCodeToCountryMap.put("+1321", "United States");
        phoneCodeToCountryMap.put("+1323", "United States");
        phoneCodeToCountryMap.put("+1325", "United States");
        phoneCodeToCountryMap.put("+1327", "United States");
        phoneCodeToCountryMap.put("+1330", "United States");
        phoneCodeToCountryMap.put("+1331", "United States");
        phoneCodeToCountryMap.put("+1334", "United States");
        phoneCodeToCountryMap.put("+1336", "United States");
        phoneCodeToCountryMap.put("+1337", "United States");
        phoneCodeToCountryMap.put("+1339", "United States");
        phoneCodeToCountryMap.put("+1346", "United States");
        phoneCodeToCountryMap.put("+1347", "United States");
        phoneCodeToCountryMap.put("+1351", "United States");
        phoneCodeToCountryMap.put("+1352", "United States");
        phoneCodeToCountryMap.put("+1360", "United States");
        phoneCodeToCountryMap.put("+1361", "United States");
        phoneCodeToCountryMap.put("+1364", "United States");
        phoneCodeToCountryMap.put("+1380", "United States");
        phoneCodeToCountryMap.put("+1385", "United States");
        phoneCodeToCountryMap.put("+1386", "United States");
        phoneCodeToCountryMap.put("+1401", "United States");
        phoneCodeToCountryMap.put("+1402", "United States");
        phoneCodeToCountryMap.put("+1404", "United States");
        phoneCodeToCountryMap.put("+1405", "United States");
        phoneCodeToCountryMap.put("+1406", "United States");
        phoneCodeToCountryMap.put("+1407", "United States");
        phoneCodeToCountryMap.put("+1408", "United States");
        phoneCodeToCountryMap.put("+1409", "United States");
        phoneCodeToCountryMap.put("+1410", "United States");
        phoneCodeToCountryMap.put("+1412", "United States");
        phoneCodeToCountryMap.put("+1413", "United States");
        phoneCodeToCountryMap.put("+1414", "United States");
        phoneCodeToCountryMap.put("+1415", "United States");
        phoneCodeToCountryMap.put("+1417", "United States");
        phoneCodeToCountryMap.put("+1419", "United States");
        phoneCodeToCountryMap.put("+1423", "United States");
        phoneCodeToCountryMap.put("+1424", "United States");
        phoneCodeToCountryMap.put("+1425", "United States");
        phoneCodeToCountryMap.put("+1430", "United States");
        phoneCodeToCountryMap.put("+1432", "United States");
        phoneCodeToCountryMap.put("+1434", "United States");
        phoneCodeToCountryMap.put("+1435", "United States");
        phoneCodeToCountryMap.put("+1440", "United States");
        phoneCodeToCountryMap.put("+1442", "United States");
        phoneCodeToCountryMap.put("+1443", "United States");
        phoneCodeToCountryMap.put("+1447", "United States");
        phoneCodeToCountryMap.put("+1458", "United States");
        phoneCodeToCountryMap.put("+1463", "United States");
        phoneCodeToCountryMap.put("+1464", "United States");
        phoneCodeToCountryMap.put("+1469", "United States");
        phoneCodeToCountryMap.put("+1470", "United States");
        phoneCodeToCountryMap.put("+1475", "United States");
        phoneCodeToCountryMap.put("+1478", "United States");
        phoneCodeToCountryMap.put("+1479", "United States");
        phoneCodeToCountryMap.put("+1480", "United States");
        phoneCodeToCountryMap.put("+1484", "United States");
        phoneCodeToCountryMap.put("+1501", "United States");
        phoneCodeToCountryMap.put("+1502", "United States");
        phoneCodeToCountryMap.put("+1503", "United States");
        phoneCodeToCountryMap.put("+1504", "United States");
        phoneCodeToCountryMap.put("+1505", "United States");
        phoneCodeToCountryMap.put("+1507", "United States");
        phoneCodeToCountryMap.put("+1508", "United States");
        phoneCodeToCountryMap.put("+1509", "United States");
        phoneCodeToCountryMap.put("+1510", "United States");
        phoneCodeToCountryMap.put("+1512", "United States");
        phoneCodeToCountryMap.put("+1513", "United States");
        phoneCodeToCountryMap.put("+1515", "United States");
        phoneCodeToCountryMap.put("+1516", "United States");
        phoneCodeToCountryMap.put("+1517", "United States");
        phoneCodeToCountryMap.put("+1518", "United States");
        phoneCodeToCountryMap.put("+1520", "United States");
        phoneCodeToCountryMap.put("+1530", "United States");
        phoneCodeToCountryMap.put("+1531", "United States");
        phoneCodeToCountryMap.put("+1534", "United States");
        phoneCodeToCountryMap.put("+1539", "United States");
        phoneCodeToCountryMap.put("+1540", "United States");
        phoneCodeToCountryMap.put("+1541", "United States");
        phoneCodeToCountryMap.put("+1551", "United States");
        phoneCodeToCountryMap.put("+1559", "United States");
        phoneCodeToCountryMap.put("+1561", "United States");
        phoneCodeToCountryMap.put("+1562", "United States");
        phoneCodeToCountryMap.put("+1563", "United States");
        phoneCodeToCountryMap.put("+1564", "United States");
        phoneCodeToCountryMap.put("+1567", "United States");
        phoneCodeToCountryMap.put("+1570", "United States");
        phoneCodeToCountryMap.put("+1571", "United States");
        phoneCodeToCountryMap.put("+1573", "United States");
        phoneCodeToCountryMap.put("+1574", "United States");
        phoneCodeToCountryMap.put("+1575", "United States");
        phoneCodeToCountryMap.put("+1580", "United States");
        phoneCodeToCountryMap.put("+1585", "United States");
        phoneCodeToCountryMap.put("+1586", "United States");
        phoneCodeToCountryMap.put("+1601", "United States");
        phoneCodeToCountryMap.put("+1602", "United States");
        phoneCodeToCountryMap.put("+1603", "United States");
        phoneCodeToCountryMap.put("+1605", "United States");
        phoneCodeToCountryMap.put("+1606", "United States");
        phoneCodeToCountryMap.put("+1607", "United States");
        phoneCodeToCountryMap.put("+1608", "United States");
        phoneCodeToCountryMap.put("+1609", "United States");
        phoneCodeToCountryMap.put("+1610", "United States");
        phoneCodeToCountryMap.put("+1612", "United States");
        phoneCodeToCountryMap.put("+1614", "United States");
        phoneCodeToCountryMap.put("+1615", "United States");
        phoneCodeToCountryMap.put("+1616", "United States");
        phoneCodeToCountryMap.put("+1617", "United States");
        phoneCodeToCountryMap.put("+1618", "United States");
        phoneCodeToCountryMap.put("+1619", "United States");
        phoneCodeToCountryMap.put("+1620", "United States");
        phoneCodeToCountryMap.put("+1623", "United States");
        phoneCodeToCountryMap.put("+1626", "United States");
        phoneCodeToCountryMap.put("+1628", "United States");
        phoneCodeToCountryMap.put("+1629", "United States");
        phoneCodeToCountryMap.put("+1630", "United States");
        phoneCodeToCountryMap.put("+1631", "United States");
        phoneCodeToCountryMap.put("+1636", "United States");
        phoneCodeToCountryMap.put("+1641", "United States");
        phoneCodeToCountryMap.put("+1646", "United States");
        phoneCodeToCountryMap.put("+1650", "United States");
        phoneCodeToCountryMap.put("+1651", "United States");
        phoneCodeToCountryMap.put("+1657", "United States");
        phoneCodeToCountryMap.put("+1660", "United States");
        phoneCodeToCountryMap.put("+1661", "United States");
        phoneCodeToCountryMap.put("+1662", "United States");
        phoneCodeToCountryMap.put("+1667", "United States");
        phoneCodeToCountryMap.put("+1669", "United States");
        phoneCodeToCountryMap.put("+1678", "United States");
        phoneCodeToCountryMap.put("+1681", "United States");
        phoneCodeToCountryMap.put("+1682", "United States");
        phoneCodeToCountryMap.put("+1701", "United States");
        phoneCodeToCountryMap.put("+1702", "United States");
        phoneCodeToCountryMap.put("+1703", "United States");
        phoneCodeToCountryMap.put("+1704", "United States");
        phoneCodeToCountryMap.put("+1706", "United States");
        phoneCodeToCountryMap.put("+1707", "United States");
        phoneCodeToCountryMap.put("+1708", "United States");
        phoneCodeToCountryMap.put("+1712", "United States");
        phoneCodeToCountryMap.put("+1713", "United States");
        phoneCodeToCountryMap.put("+1714", "United States");
        phoneCodeToCountryMap.put("+1715", "United States");
        phoneCodeToCountryMap.put("+1716", "United States");
        phoneCodeToCountryMap.put("+1717", "United States");
        phoneCodeToCountryMap.put("+1718", "United States");
        phoneCodeToCountryMap.put("+1719", "United States");
        phoneCodeToCountryMap.put("+1720", "United States");
        phoneCodeToCountryMap.put("+1724", "United States");
        phoneCodeToCountryMap.put("+1725", "United States");
        phoneCodeToCountryMap.put("+1727", "United States");
        phoneCodeToCountryMap.put("+1730", "United States");
        phoneCodeToCountryMap.put("+1731", "United States");
        phoneCodeToCountryMap.put("+1732", "United States");
        phoneCodeToCountryMap.put("+1734", "United States");
        phoneCodeToCountryMap.put("+1737", "United States");
        phoneCodeToCountryMap.put("+1740", "United States");
        phoneCodeToCountryMap.put("+1743", "United States");
        phoneCodeToCountryMap.put("+1747", "United States");
        phoneCodeToCountryMap.put("+1754", "United States");
        phoneCodeToCountryMap.put("+1757", "United States");
        phoneCodeToCountryMap.put("+1760", "United States");
        phoneCodeToCountryMap.put("+1762", "United States");
        phoneCodeToCountryMap.put("+1763", "United States");
        phoneCodeToCountryMap.put("+1765", "United States");
        phoneCodeToCountryMap.put("+1769", "United States");
        phoneCodeToCountryMap.put("+1770", "United States");
        phoneCodeToCountryMap.put("+1772", "United States");
        phoneCodeToCountryMap.put("+1773", "United States");
        phoneCodeToCountryMap.put("+1774", "United States");
        phoneCodeToCountryMap.put("+1775", "United States");
        phoneCodeToCountryMap.put("+1779", "United States");
        phoneCodeToCountryMap.put("+1781", "United States");
        phoneCodeToCountryMap.put("+1785", "United States");
        phoneCodeToCountryMap.put("+1786", "United States");
        phoneCodeToCountryMap.put("+1801", "United States");
        phoneCodeToCountryMap.put("+1802", "United States");
        phoneCodeToCountryMap.put("+1803", "United States");
        phoneCodeToCountryMap.put("+1804", "United States");
        phoneCodeToCountryMap.put("+1805", "United States");
        phoneCodeToCountryMap.put("+1806", "United States");
        phoneCodeToCountryMap.put("+1808", "United States");
        phoneCodeToCountryMap.put("+1810", "United States");
        phoneCodeToCountryMap.put("+1812", "United States");
        phoneCodeToCountryMap.put("+1813", "United States");
        phoneCodeToCountryMap.put("+1814", "United States");
        phoneCodeToCountryMap.put("+1815", "United States");
        phoneCodeToCountryMap.put("+1816", "United States");
        phoneCodeToCountryMap.put("+1817", "United States");
        phoneCodeToCountryMap.put("+1818", "United States");
        phoneCodeToCountryMap.put("+1828", "United States");
        phoneCodeToCountryMap.put("+1830", "United States");
        phoneCodeToCountryMap.put("+1831", "United States");
        phoneCodeToCountryMap.put("+1832", "United States");
        phoneCodeToCountryMap.put("+1843", "United States");
        phoneCodeToCountryMap.put("+1845", "United States");
        phoneCodeToCountryMap.put("+1847", "United States");
        phoneCodeToCountryMap.put("+1848", "United States");
        phoneCodeToCountryMap.put("+1850", "United States");
        phoneCodeToCountryMap.put("+1854", "United States");
        phoneCodeToCountryMap.put("+1856", "United States");
        phoneCodeToCountryMap.put("+1857", "United States");
        phoneCodeToCountryMap.put("+1858", "United States");
        phoneCodeToCountryMap.put("+1859", "United States");
        phoneCodeToCountryMap.put("+1860", "United States");
        phoneCodeToCountryMap.put("+1862", "United States");
        phoneCodeToCountryMap.put("+1863", "United States");
        phoneCodeToCountryMap.put("+1864", "United States");
        phoneCodeToCountryMap.put("+1865", "United States");
        phoneCodeToCountryMap.put("+1870", "United States");
        phoneCodeToCountryMap.put("+1872", "United States");
        phoneCodeToCountryMap.put("+1878", "United States");
        phoneCodeToCountryMap.put("+1901", "United States");
        phoneCodeToCountryMap.put("+1903", "United States");
        phoneCodeToCountryMap.put("+1904", "United States");
        phoneCodeToCountryMap.put("+1906", "United States");
        phoneCodeToCountryMap.put("+1907", "United States");
        phoneCodeToCountryMap.put("+1908", "United States");
        phoneCodeToCountryMap.put("+1909", "United States");
        phoneCodeToCountryMap.put("+1910", "United States");
        phoneCodeToCountryMap.put("+1912", "United States");
        phoneCodeToCountryMap.put("+1913", "United States");
        phoneCodeToCountryMap.put("+1914", "United States");
        phoneCodeToCountryMap.put("+1915", "United States");
        phoneCodeToCountryMap.put("+1916", "United States");
        phoneCodeToCountryMap.put("+1917", "United States");
        phoneCodeToCountryMap.put("+1918", "United States");
        phoneCodeToCountryMap.put("+1919", "United States");
        phoneCodeToCountryMap.put("+1920", "United States");
        phoneCodeToCountryMap.put("+1925", "United States");
        phoneCodeToCountryMap.put("+1928", "United States");
        phoneCodeToCountryMap.put("+1929", "United States");
        phoneCodeToCountryMap.put("+1930", "United States");
        phoneCodeToCountryMap.put("+1931", "United States");
        phoneCodeToCountryMap.put("+1934", "United States");
        phoneCodeToCountryMap.put("+1936", "United States");
        phoneCodeToCountryMap.put("+1937", "United States");
        phoneCodeToCountryMap.put("+1938", "United States");
        phoneCodeToCountryMap.put("+1940", "United States");
        phoneCodeToCountryMap.put("+1941", "United States");
        phoneCodeToCountryMap.put("+1947", "United States");
        phoneCodeToCountryMap.put("+1949", "United States");
        phoneCodeToCountryMap.put("+1951", "United States");
        phoneCodeToCountryMap.put("+1952", "United States");
        phoneCodeToCountryMap.put("+1954", "United States");
        phoneCodeToCountryMap.put("+1956", "United States");
        phoneCodeToCountryMap.put("+1959", "United States");
        phoneCodeToCountryMap.put("+1970", "United States");
        phoneCodeToCountryMap.put("+1971", "United States");
        phoneCodeToCountryMap.put("+1972", "United States");
        phoneCodeToCountryMap.put("+1973", "United States");
        phoneCodeToCountryMap.put("+1975", "United States");
        phoneCodeToCountryMap.put("+1978", "United States");
        phoneCodeToCountryMap.put("+1979", "United States");
        phoneCodeToCountryMap.put("+1980", "United States");
        phoneCodeToCountryMap.put("+1984", "United States");
        phoneCodeToCountryMap.put("+1985", "United States");
        phoneCodeToCountryMap.put("+1989", "United States");
        phoneCodeToCountryMap.put("+54", "Argentina");
        phoneCodeToCountryMap.put("+226", "Burkina Faso");
        phoneCodeToCountryMap.put("+1441", "Bermuda");
        phoneCodeToCountryMap.put("+505", "Nicaragua");
        phoneCodeToCountryMap.put("+973", "Bahrain");
        phoneCodeToCountryMap.put("+254", "Kenya");
        phoneCodeToCountryMap.put("+381", "Serbia");
        phoneCodeToCountryMap.put("+670", "Timor Leste");
        phoneCodeToCountryMap.put("+1767", "Dominica");
        phoneCodeToCountryMap.put("+1670", "Northern Mariana Islands");
        phoneCodeToCountryMap.put("+63", "Philippines");
        phoneCodeToCountryMap.put("+383", "Kosovo");
        phoneCodeToCountryMap.put("+965", "Kuwait");
        phoneCodeToCountryMap.put("+886", "Taiwan");
        phoneCodeToCountryMap.put("+257", "Burundi");
        phoneCodeToCountryMap.put("+1684", "American Samoa");
        phoneCodeToCountryMap.put("+359", "Bulgaria");
        phoneCodeToCountryMap.put("+27", "South Africa");
        phoneCodeToCountryMap.put("+51", "Peru");
        phoneCodeToCountryMap.put("+297", "Aruba");
        phoneCodeToCountryMap.put("+233", "Ghana");
        phoneCodeToCountryMap.put("+590", "Saint Martin");
        phoneCodeToCountryMap.put("+91", "India");
        phoneCodeToCountryMap.put("+256", "Uganda");


        countryNames.addAll(phoneCodeToCountryMap.values());
        countryNames.add("Country not found");
    } public Map<String, String> getPhoneCodeToCountryMap() {
        return phoneCodeToCountryMap;
    }

    public List<String> getCountryNames() {
        return countryNames;
    }
}