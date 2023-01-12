public class Xap {

    public static void main(String[] args) {
        Zap z = new Zap();
        System.out.println(z.branch());
    }

    public String branch() {
        return "Xap";
    }
    
}

class Yap extends Xap {

    @Override
    public String branch() {
        return super.branch() + " <- Yap";
    }

}

class Zap extends Yap {
    
    @Override
    public String branch() {
        return super.branch() + " <- Zap";
    }
    
}
