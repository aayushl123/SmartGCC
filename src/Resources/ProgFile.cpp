#include "HeaderFile.h"
#include <iostream>
extern int myfunc(); //external library functions have to be mentioned

int main() {
    //int num = NUM;
    std::cout << "main() says Hello World!\n"<<
    "The number in the header file is " << NUM<<"\n";
    myfunc();
    return 0;
}