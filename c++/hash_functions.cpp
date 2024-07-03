#include <iostream>

int hash01(int x, int y) {
    return x < y ? y*y+2*y-x : x*x+y;
}

int hash02(int x, int y) {
    return ((x*(x+1)+y*(y+1))>>1)+x*(y+1);
}

int hash03(int x, int y) {
    int z = 2*(x+1)*((1<<y)-1);
    for (int d = 1; d <= x; d <<= 1) {
        z += x/d;
    }
    return z;
}

int hash04(int x, int y) {
	return (2*x+1)*(1<<y)-1;
}

int main() {
    int len = 20;
    for (int r = 0; r < len; ++r) {
        for (int c = 0; c < len>>2; ++c) {
            std::cout << hash04(r, c) << ' ';
        }
        std::cout << '\n';
    }
}
