package cmput301f17t12.quirks.Enumerations;

public enum DropType {
    // Common items
    jeremylin(40, "Jeremy Lin"),
    kylesingler(40, "Kyle Singler"),
    zhouqi(40, "Zhou Qi"),
    krisdunn(40, "Kris Dunn"),
    joshhart(40, "Josh Hart"),
    jordanbell(40, "Jordan Bell"),
    kylekorver(40, "Kyle Korver"),
    coreybrewer(40, "Corey Brewer"),
    davidnwaba(40, "David Nwaba"),
    jakobpoeltl(40, "Jakob Poeltl"),

    // Uncommon items
    thonmaker(20, "Thon Maker"),
    jrsmith(20, "J.R. Smith"),
    jaylenbrown(20, "Jaylen Brown"),
    rajonrondo(20, "Rajon Rondo"),
    michaelkiddgilchrist(20, "Michael Kidd-Gilchrist"),
    manuginobli(20, "Manu Ginobli"),
    skallabissiere(20, "Skal Labissiere"),
    davidwest(20, "David West"),
    deaaronfox(20, "De'Aaron Fox"),

    // Rare items
    jordanclarkson(15, "Jordan Clarkson"),
    kcp(15, "Kentavious Caldwell-Pope"),
    buddyhield(15, "Buddy Hield"),
    lonzoball(15, "Lonzo Ball"),
    tarikblack(15, "Tarik Black"),
    bismackbiyombo(15, "Bismack Biyombo"),
    tysonchandler(15, "Tyson Chandler"),
    tyrekeevans(15, "Tyreke Evans"),
    ekpeudoh(15, "Ekpe Udoh"),
    larrynance(15, "Larry Nance Jr."),

    // Mythical items
    giannis(10, "Giannis Antetokounmpo"),
    draymond(10, "Draymond Green"),
    ingram(10, "Brandon Ingram"),
    dangelo(10, "D'Angelo Russell"),
    paugasol(10, "Pau Gasol"),
    averybradley(10, "Avery Bradley"),
    bradleybeal(10, "Bradley Beal"),
    markkanen(10, "Lauri Markkanen"),
    kylekuzma(10, "Kyle Kuzma"),


    // Legendary items
    durant(5, "Kevin Durant"),
    curry(5, "Steph Curry"),
    harden(5, "James Harden"),
    cousins(5, "DeMarcus Cousins"),
    davis(5, "Anthony Davis"),
    westbrook(5, "Russell Westbrook"),
    kawhi(5, "Kawhi Leonard"),
    derozan(5, "Demar Derozan"),
    carmelo(5, "Carmelo Anthony"),
    paulgeorge(5, "Paul George"),

    // Ancient items
    kobe(1, "Kobe Bryant"),
    jordan(1, "Michael Jordan"),
    lebron(1, "Lebron James"),
    magic(1, "Magic Johnson"),
    kareem(1, "Kareem Abdul-Jabar"),
    wilt(1, "Wilt Chamberlain"),
    larrybird(1, "Larry Bird"),
    duncan(1, "Tim Duncan"),
    hakeem(1, "Hakeem Olajuwon"),
    shaq(1, "Shaquille O'neal");

    private final double randomWeight;
    private final String name;

    DropType(double randomWeight, String name) {
        this.randomWeight = randomWeight;
        this.name = name;
    }

    public double getWeight() { return this.randomWeight; }

    public String getName() { return this.name; }

    public String getType() {
        if (this.randomWeight == 40) {
            return "COMMON";
        } else if (this.randomWeight == 20) {
            return "UNCOMMON";
        } else if (this.randomWeight == 15) {
            return "RARE";
        } else if (this.randomWeight == 10) {
            return "MYTHICAL";
        } else if (this.randomWeight == 5) {
            return "LEGENDARY";
        } else if (this.randomWeight == 1) {
            return "ANCIENT";
        } else {
            return "";
        }
    }
}
