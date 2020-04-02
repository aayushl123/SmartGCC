#include "HeaderFile.h"
#include <iostream>
double encrypt(int val)
{
    std::cout<<"encypt() says HELLO WORLD\n";
    double encVal= ((double)ENCRPTION_STANDARD/(double)val);
    return encVal;
}
int decrypt(double encVal){
    std::cout<<"decrypt() says HELLO WORLD\n";
    double decVal=((double)ENCRPTION_STANDARD/(double)encVal);
    int returnVal = (int) decVal;
    return decVal;
}