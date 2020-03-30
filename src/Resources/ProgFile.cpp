#include "HeaderFile.h"
#include <iostream>
extern double encrypt(int); //external library functions have to be mentioned
extern int decrypt(double); //external library functions have to be mentioned

int takeVal(){
    int val;
    std::cout<<"Input the value to be encrypted\n";
    std::cin>> val;
    return val;
}

int main() {
    std::cout << "main() says Hello World!\n"<<
    "The encryption standard is " << ENCRPTION_STANDARD<<"\n";
    int val = takeVal();
    double encVal = encrypt(val);
    int decVal = decrypt(encVal);
    if(decVal == val){
        std::cout<<"Both encoding and decoding were successful\n";
    }
    else{
         std::cout<<"Encoding and decoding was NOT successful\n";
    }
    return 0;
}