const {
  readInput,
  writeOutput,
  convertTextToNumber,
  getDigitFromLine,
  getRegexForUseCase,
  UseCase,
  main,
} = require('../src/utils');

const pathToInputFolder = 'input/';
const pathToOutputFolder = 'output/';

describe('readInput', () => {
  it('should read input file and return the content', async () => {
    const path = pathToInputFolder + 'input.test.txt';
    const expectedContent = '1abc2\npqr3stu8vwx\na1b2c3d4e5f\ntreb7uchet';

    const content = await readInput(path);

    expect(content).toEqual(expectedContent);
  });

  it('should throw an error if the input file does not exist', async () => {
    const path = pathToInputFolder + 'nonexistent.txt';

    const result = await readInput(path);

    expect(result).toBe(null);
  });
});

describe('writeOutput', () => {
  it('should write data to the output file', async () => {
    const path = pathToOutputFolder + 'output.test.txt';
    const data = 'Hello, World!';

    const result = await writeOutput(path, data);
    const resultData = await readInput(path);

    expect(result).toBe(true);
    expect(resultData).toEqual(data);
  });
});

describe('convertTextToNumber', () => {
  it('should convert text to number', () => {
    const text = 'one';

    const result = convertTextToNumber(text);

    expect(result).toEqual(1);
  });

  it('should return null if the text is not a number', () => {
    const text = 'abc';

    const result = convertTextToNumber(text);

    expect(result).toBe(null);
  });
});

describe('getRegexForUseCase', () => {
  it('should return regex for use case FIRST', () => {
    const result = getRegexForUseCase(UseCase.FIRST, false);
    expect(result).toEqual(/\d/);
  });

  it('should return regex for use case LAST', () => {
    const result = getRegexForUseCase(UseCase.LAST, false);
    expect(result).toEqual(/\d(?=\D*$)/);
  });

  it('should return regex for use case FIRST with text search', () => {
    const result = getRegexForUseCase(UseCase.FIRST, true);
    expect(result).toEqual(/\d|one|two|three|four|five|six|seven|eight|nine/);
  });
});

describe('getDigitFromLine', () => {
  // test cases for FIRST use case without text search
  it('should return null if there is no digit in line', () => {
    const result = getDigitFromLine('abc', UseCase.FIRST, false);
    expect(result).toBe(null);
  });

  it('should return digit from line', () => {
    const result = getDigitFromLine('1abc2', UseCase.FIRST, false);
    expect(result).toEqual(1);
  });

  // test cases for LAST use case without text search
  it('should return null if there is no digit in line', () => {
    const result = getDigitFromLine('abc', UseCase.LAST, false);
    expect(result).toBe(null);
  });

  it('should return digit from line', () => {
    const result = getDigitFromLine('1abc2', UseCase.LAST, false);
    expect(result).toEqual(2);
  });

  // test cases for FIRST use case with text search
  it('should return null if there is no digit in line', () => {
    const result = getDigitFromLine('abc', UseCase.FIRST, true);
    expect(result).toBe(null);
  });

  it('should return digit from line', () => {
    const result = getDigitFromLine('oneabc2', UseCase.FIRST, true);
    expect(result).toEqual(1);
  });

  it('should return digit from line', () => {
    const result = getDigitFromLine('2oneabc3', UseCase.FIRST, true);
    expect(result).toEqual(2);
  });

  it('should return digit from line', () => {
    const result = getDigitFromLine(
      'two65eightbkgqcsn91qxkfvg',
      UseCase.FIRST,
      true
    );
    expect(result).toEqual(2);
  });

  // test cases for LAST use case with text search
  it('should return null if there is no digit in line', () => {
    const result = getDigitFromLine('abc', UseCase.LAST, true);
    expect(result).toBe(null);
  });

  it('should return digit from line', () => {
    const result = getDigitFromLine('oneabc2', UseCase.LAST, true);
    expect(result).toEqual(2);
  });

  it('should return digit from line', () => {
    const result = getDigitFromLine('2abc3one', UseCase.LAST, true);
    expect(result).toEqual(1);
  });

  it('should return digit from line', () => {
    const result = getDigitFromLine(
      '126dzbvg6two4oneightntd',
      UseCase.LAST,
      true
    );
    expect(result).toEqual(8);
  });

  it('should return digit from line', () => {
    const result = getDigitFromLine(
      'neightwompstbkqv1fourfthdcfgtrkqzgrbfrczxbdn',
      UseCase.LAST,
      true
    );
    expect(result).toEqual(4);
  });
});

describe('main', () => {
  it('should read test input file and write result to output file', async () => {
    const inPath = pathToInputFolder + 'input.test.txt';
    const outPath = pathToOutputFolder + 'output.test.txt';

    await main(inPath, outPath), false;
    const content = await readInput(outPath);

    expect(content).toEqual('142');
  });

  it('should read test input file and write result to output file with text search', async () => {
    const inPath = pathToInputFolder + 'input2.test.txt';
    const outPath = pathToOutputFolder + 'output.test.txt';

    await main(inPath, outPath, true);
    const content = await readInput(outPath);

    expect(content).toEqual('281');
  });
});
