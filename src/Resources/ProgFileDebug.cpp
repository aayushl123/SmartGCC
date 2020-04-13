#include <iostream>
#include <string>
int main() {
    int sub1 = 100;
    int sub2 = 98;
    int sub3 = 95;
    int sub4 = 65;
    int sub5 = 84;
    int sub6 = 86;
    std::string output = "Hello World! We will analyze the grade report of a student\n";
    int sumOfAll = sub1 +sub2 +sub3 +sub4 +sub5 +sub6;
    output = output + "Sum of all marks obtained in all the subjects is: " + std::to_string(sumOfAll) + "\n";
    double percentage = sumOfAll/6;
    output = output + "The overall percentage is: " + std::to_string(percentage) + "\n";
    if(percentage < 60){
        output = output +"The grade obtained is: F\n";}
    if(percentage >=60 && percentage <65){
        output = output +"The grade obtained is: D\n";}
    if(percentage >=65 && percentage <70){
        output = output + "The grade obtained is: C\n";}
    if(percentage >=70 && percentage <75){
        output = output + "The grade obtained is: B-\n";}
    if(percentage >=75 && percentage <80){
        output = output + "The grade obtained is: B\n";}
    if(percentage >=80 && percentage <85){
        output = output + "The grade obtained is: B+\n";}
    if(percentage >=85 && percentage <90){
        output = output + "The grade obtained is: A-\n";}
    if(percentage >=90){
        output = output + "The grade obtained is: A+\n";}

    std::cout << output;

    return 0;
}

