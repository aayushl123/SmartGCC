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
    std::cout<<"The value to be encoded is "<<val<<"\n";
    double encVal = encrypt(val);
    std::cout<<"The encoded value is "<<encVal<<"\n";
    int decVal = decrypt(encVal);
    std::cout<<"The decoded value is "<<decVal<<"\n";
    if(decVal == val){
        std::cout<<"Both encoding and decoding were successful\n";
    }
    else{
         std::cout<<"Encoding and decoding was NOT successful\n";
    }
    return 0;
}

